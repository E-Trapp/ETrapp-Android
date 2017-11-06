package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Mockups;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;

public class CreateOrEditEvent extends BaseActivity {

    private Event event;

    public static Intent startEditMode(Context context, long eventKey) {
        Intent i = new Intent(context, CreateOrEditEvent.class);
        i.putExtra(EXTRA_EVENT_ID, eventKey);
        return i;
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

    private void handleIntent(Intent i) {
        if (i != null) {
            if (i.hasExtra(EXTRA_EVENT_ID)) {
                event = Mockups.getEventById(i.getLongExtra(EXTRA_EVENT_ID, -1));
                getCurrentActionBar().setTitle(getString(R.string.edit));
            } else {
                getCurrentActionBar().setTitle(getString(R.string.create_event));
            }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
