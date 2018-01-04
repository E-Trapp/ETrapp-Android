package cat.udl.eps.etrapp.android.ui.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Toaster;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;
import static cat.udl.eps.etrapp.android.utils.Constants.PERMISSION_ACESS_FINE_LOCATION_REQUEST;
import static cat.udl.eps.etrapp.android.utils.Constants.PLACE_PICKER_REQUEST;

public class CreateOrEditEvent extends BaseActivity
        implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    protected GeoDataClient mGeoDataClient;
    protected PlaceDetectionClient mPlaceDetectionClient;
    @BindView(R.id.create_event_title) EditText eventTitle;
    @BindView(R.id.create_event_description) EditText eventDescription;
    @BindView(R.id.create_event_date) EditText eventDate;
    @BindView(R.id.create_event_location) EditText eventLocation;
    @BindView(R.id.create_event_time) EditText eventTime;
    @BindView(R.id.create_event_image) EditText eventImage;
    @BindView(R.id.event_create_button) Button event_create_button;
    private GoogleApiClient mGoogleApiClient;
    private Event event;
    private boolean playServicesAvailable = false;

    public static Intent startEditMode(Context context, long eventKey) {
        Intent i = new Intent(context, CreateOrEditEvent.class);
        i.putExtra(EXTRA_EVENT_ID, eventKey);
        return i;
    }

    public static Intent startCreateMode(Context context) {
        return new Intent(context, CreateOrEditEvent.class);
    }

    @Override protected int getLayout() {
        return R.layout.activity_create_edit_event;
    }

    @Override protected void configView() {
        handleIntent(getIntent());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (code != ConnectionResult.SUCCESS) {
            GoogleApiAvailability.getInstance().getErrorDialog(this, code, 1).show();
        } else {
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .enableAutoManage(this, this)
                    .build();
            playServicesAvailable = true;
            mGeoDataClient = Places.getGeoDataClient(this, null);
            mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        }

        eventLocation.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACESS_FINE_LOCATION_REQUEST);
                return;
            }
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        });
        eventTime.setOnClickListener(view -> {
            DialogFragment newFragment = TimePickerFragment.newInstance(this);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });
        eventDate.setOnClickListener(view -> {
            DialogFragment newFragment = DatePickerFragment.newInstance(this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });
        event_create_button.setOnClickListener(view -> createEvent());
    }

    private void setupUI() {
        eventTitle.setText(event.getTitle());
        eventDescription.setText(event.getDescription());

        Date d = new Date(event.getStartsAt());
        eventDate.setText(dateFormat.format(d));
        eventTime.setText(timeFormat.format(d));

        event_create_button.setText(getString(R.string.save_changes));
        event_create_button.setOnClickListener(view -> {
            Map<String, Object> updates = new HashMap<>();
            long newTime = 0;
            try {
                String dateString = eventDate.getText().toString() + " " + eventTime.getText().toString();
                newTime = fullFormat.parse(dateString).getTime();
                updates.put("startsAt", newTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // TODO: Handle every field to be updated.
            if (!eventTitle.getText().toString().equals(event.getTitle())) {
                updates.put("title", eventTitle.getText().toString());
            }

            if (!eventDescription.getText().toString().equals(event.getTitle())) {
                updates.put("description", eventDescription.getText().toString());
            }

            if (!eventLocation.getText().toString().equals(event.getLocation())) {
                updates.put("location", eventLocation.getText().toString());
            }

            if (!eventImage.getText().toString().equals(event.getImageUrl())) {
                updates.put("imageUrl", eventImage.getText().toString());
            }

            EventController.getInstance()
                    .editEvent(event.getId(), updates)
                    .addOnSuccessListener(aVoid -> {
                        Toaster.show(this, "Event updated");
                        setResult(RESULT_OK);
                        finish();
                    });
        });
    }

    private void handleIntent(Intent i) {
        if (i != null) {
            if (i.hasExtra(EXTRA_EVENT_ID)) {
                getCurrentActionBar().setTitle(getString(R.string.edit));
                EventController.getInstance()
                        .getEventById(i.getLongExtra(EXTRA_EVENT_ID, -1))
                        .addOnSuccessListener(e -> {
                            event = e;
                            setupUI();
                        })
                        .addOnFailureListener(e -> {
                            Toaster.show(this, "Something went wrong. Event not found.");
                            finish();
                        });
            } else {
                getCurrentActionBar().setTitle(getString(R.string.create_event));
                event_create_button.setText(getString(R.string.create_event));
            }
        }
    }

    private void createEvent() {

        // TODO: Handle empty required fields, errors and date.

        event = new Event();
        event.setTitle(eventTitle.getText().toString());
        event.setDescription(eventDescription.getText().toString());
        event.setImageUrl(eventImage.getText().toString());
        event.setLocation(eventLocation.getText().toString());
        event.setOwner(UserController.getInstance().getCurrentUser().getId());
        try {
            long newTime = 0;
            String dateString = eventDate.getText().toString() + " " + eventTime.getText().toString();
            newTime = fullFormat.parse(dateString).getTime();
            event.setStartsAt(newTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EventController.getInstance()
                .createEvent(event)
                .addOnSuccessListener(aVoid -> {
                    Toaster.show(this, "Event created");
                    setResult(RESULT_OK);
                    finish();
                });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Timber.d("Selected date: %d-%d-%d", year, month, dayOfMonth);
        eventDate.setText(String.format(Locale.getDefault(), "%d-%d-%d", year, (month + 1), dayOfMonth));
    }

    @Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Timber.d("Selected time: %d:%d", hour, minute);
        eventTime.setText(String.format(Locale.getDefault(), " %d:%02d", hour, minute));
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                eventLocation.setText(place.getAddress());
            }
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACESS_FINE_LOCATION_REQUEST:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public static class TimePickerFragment extends DialogFragment {

        private TimePickerDialog.OnTimeSetListener listener;

        public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener) {
            TimePickerFragment fragment = new TimePickerFragment();
            fragment.listener = listener;
            return fragment;
        }

        @NonNull @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), listener, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.listener = listener;
            return fragment;
        }

        @NonNull @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }
}
