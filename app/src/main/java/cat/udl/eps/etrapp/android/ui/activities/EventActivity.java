package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Mockups;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;
import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_EDIT_EVENT;

public class EventActivity extends BaseActivity {

    private Event event;

    public static Intent start(Context context, long eventKey) {
        Intent i = new Intent(context, EventActivity.class);
        i.putExtra(EXTRA_EVENT_ID, eventKey);
        return i;
    }

    @Override protected int getLayout() {
        return R.layout.activity_event;
    }

    @Override protected void configView() {
        handleIntent(getIntent());
        getCurrentActionBar().setTitle(event.getTitle());
    }

    private void handleIntent(Intent intent) {
        if (intent == null) return;
        if (intent.hasExtra(EXTRA_EVENT_ID)) {
            event = Mockups.getEventById(intent.getLongExtra(EXTRA_EVENT_ID, -1));
        } else {
            finish();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, ID_MENU_ITEM_EDIT_EVENT, 50, R.string.edit)
                .setIcon(R.drawable.ic_pencil_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_ITEM_EDIT_EVENT:
                startActivity(CreateOrEditEvent.startEditMode(this, event.getId()));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
