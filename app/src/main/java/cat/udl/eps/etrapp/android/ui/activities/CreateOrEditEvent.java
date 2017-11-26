package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Toaster;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;

public class CreateOrEditEvent extends BaseActivity {


    @BindView(R.id.create_event_title) EditText eventTitle;
    @BindView(R.id.create_event_description) EditText eventDescription;
    @BindView(R.id.create_event_datetime) EditText eventDatetime;
    @BindView(R.id.create_event_image) EditText eventImage;

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
    }

    private void setupUI() {
        eventTitle.setText(event.getTitle());
        eventDescription.setText(event.getDescription());

        // TODO: Fill every field with the actual data.
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
            }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
