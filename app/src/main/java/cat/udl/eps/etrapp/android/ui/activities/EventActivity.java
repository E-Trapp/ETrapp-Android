package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.thedeanda.lorem.LoremIpsum;

import butterknife.BindView;
import butterknife.OnClick;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.StreamMessage;
import cat.udl.eps.etrapp.android.ui.adapters.EventStreamAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Mockups;
import cat.udl.eps.etrapp.android.utils.Toaster;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;
import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_EDIT_EVENT;

public class EventActivity extends BaseActivity {

    @BindView(R.id.event_header_user_name) TextView userName;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    Handler handler = new Handler(Looper.getMainLooper());
    private Event event;
    private EventStreamAdapter eventStreamAdapter;

    public static Intent start(Context context, long eventKey) {
        Intent i = new Intent(context, EventActivity.class);
        i.putExtra(EXTRA_EVENT_ID, eventKey);
        return i;
    }

    @OnClick(R.id.event_header_container) void onClickHeader() {
        startActivity(UserProfileActivity.start(this, event.getOwner()));
    }

    @OnClick(R.id.event_header_rate_user_up) void voteUp() {
        Toaster.show(this, "ETrapper Upvoted!");
    }

    @OnClick(R.id.event_header_rate_user_down) void voteDown() {
        Toaster.show(this, "ETrapper Downvoted!");
    }

    @Override protected int getLayout() {
        return R.layout.activity_event;
    }

    @Override protected void configView() {
        handleIntent(getIntent());
        getCurrentActionBar().setTitle(event.getTitle());
        userName.setText(Mockups.getUserById(event.getOwner()).getUsername());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        eventStreamAdapter = new EventStreamAdapter();
        recyclerView.setAdapter(eventStreamAdapter);

        new Thread(() -> {
            while (true) {
                handler.post(() -> eventStreamAdapter.insertMessage(new StreamMessage(System.currentTimeMillis(), LoremIpsum.getInstance().getWords(5, 30))));
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
