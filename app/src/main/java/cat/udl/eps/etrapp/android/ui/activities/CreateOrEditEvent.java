package cat.udl.eps.etrapp.android.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnSuccessListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Toaster;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;

public class CreateOrEditEvent extends BaseActivity
        implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private static final SimpleDateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    @BindView(R.id.create_event_title) EditText eventTitle;
    @BindView(R.id.create_event_description) EditText eventDescription;
    @BindView(R.id.create_event_date) EditText eventDate;
    @BindView(R.id.create_event_location) EditText eventLocation;
    @BindView(R.id.create_event_time) EditText eventTime;
    @BindView(R.id.create_event_image) EditText eventImage;
    @BindView(R.id.event_create_button) Button event_create_button;
    private Event event;

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

        eventLocation.setOnClickListener(view -> Timber.d("HUE"));
        eventTime.setOnClickListener(view -> {
            DialogFragment newFragment = TimePickerFragment.newInstance(this);
            newFragment.show(getSupportFragmentManager(), "timePicker");
        });
        eventDate.setOnClickListener(view -> {
            DialogFragment newFragment = DatePickerFragment.newInstance(this);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        });
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
                String dateString = eventDate.getText().toString()+ " " + eventTime.getText().toString();
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

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Timber.d("Selected date: %d-%d-%d", year, month, dayOfMonth);
        eventDate.setText(String.format("%d-%d-%d", year, (month+1), dayOfMonth));
    }

    @Override public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Timber.d("Selected time: %d:%d", hour, minute);
        eventTime.setText(String.format(" %d:%02d", hour, minute));
    }

    public static class TimePickerFragment extends DialogFragment {

        private TimePickerDialog.OnTimeSetListener listener;

        public static TimePickerFragment newInstance(TimePickerDialog.OnTimeSetListener listener) {
            TimePickerFragment fragment = new TimePickerFragment();
            fragment.listener = listener;
            return fragment;
        }

        @Override
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

        @Override
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
