package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.adapters.EventPagerAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.ui.fragments.event.EventFragment;
import cat.udl.eps.etrapp.android.utils.Toaster;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;
import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_EDIT_EVENT;
import static cat.udl.eps.etrapp.android.utils.Constants.RC_EDIT_EVENT;

public class EventActivity extends BaseActivity {

    @BindView(R.id.eventPager) ViewPager pager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    private Menu menu;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent == null) return;
        if (intent.hasExtra(EXTRA_EVENT_ID)) {
            EventController.getInstance()
                    .getEventById(intent.getLongExtra(EXTRA_EVENT_ID, -1))
                    .addOnSuccessListener(e -> {
                        event = e;
                        setupUI();
                    })
                    .addOnFailureListener(e -> {
                        Toaster.show(this, "Something went wrong. Event not found.");
                        finish();
                    });
        } else {
            finish();
        }
    }

    private void setupUI() {
        getCurrentActionBar().setTitle(event.getTitle());
        if (UserController.getInstance().isUserLoggedIn()) {
            if (UserController.getInstance().getCurrentUser().getId() == event.getOwner()) {
                menu.getItem(0).setVisible(true);
            }
        }

        pager.setAdapter(new EventPagerAdapter(getSupportFragmentManager(), event));
        tabLayout.setupWithViewPager(pager);
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_EDIT_EVENT:
                if (resultCode == RESULT_OK) {
                    recreate();
                }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, ID_MENU_ITEM_EDIT_EVENT, 50, R.string.edit)
                .setIcon(R.drawable.ic_pencil_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.getItem(0).setVisible(false);

        this.menu = menu;

        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_ITEM_EDIT_EVENT:
                startActivityForResult(CreateOrEditEvent.startEditMode(this, event.getId()), RC_EDIT_EVENT);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


}
