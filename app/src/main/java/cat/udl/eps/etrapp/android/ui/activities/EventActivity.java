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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedeanda.lorem.LoremIpsum;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.StreamMessage;
import cat.udl.eps.etrapp.android.ui.adapters.EventStreamAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Mockups;
import cat.udl.eps.etrapp.android.utils.Toaster;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;
import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_EDIT_EVENT;

public class EventActivity extends BaseActivity {

    @BindView(R.id.event_stream_header) ViewGroup header;
    @BindView(R.id.event_stream_recycler) RecyclerView recyclerView;
    @BindView(R.id.event_stream_send_container) ViewGroup sendContainer;
    Handler handler = new Handler(Looper.getMainLooper());
    Menu menu;
    private Event event;
    private EventStreamAdapter eventStreamAdapter;
    private TextView userName;
    private ImageView rateUp;
    private ImageView rateDown;

    public static Intent start(Context context, long eventKey) {
        Intent i = new Intent(context, EventActivity.class);
        i.putExtra(EXTRA_EVENT_ID, eventKey);
        return i;
    }

    @Override protected int getLayout() {
        return R.layout.activity_event;
    }

    @Override protected void configView() {
        userName = header.findViewById(R.id.event_header_user_name);
        rateUp = header.findViewById(R.id.event_header_rate_user_up);
        rateDown = header.findViewById(R.id.event_header_rate_user_down);

        View.OnClickListener clickListener = view -> {
            switch (view.getId()) {
                case R.id.event_stream_header:
                    startActivity(UserProfileActivity.start(this, event.getOwner()));
                    break;
                case R.id.event_header_rate_user_up:
                    Toaster.show(this, "ETrapper Upvoted!");
                    break;
                case R.id.event_header_rate_user_down:
                    Toaster.show(this, "ETrapper Downvoted!");
                    break;
            }
        };


        header.setOnClickListener(clickListener);
        rateUp.setOnClickListener(clickListener);
        rateDown.setOnClickListener(clickListener);

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

        if (Mockups.isUserLoggedIn() && Mockups.getCurrentUser().getId() == event.getId()) {
            menu.getItem(0).setVisible(true);
        }

        userName.setText(Mockups.getUserById(event.getOwner()).getUsername());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        eventStreamAdapter = new EventStreamAdapter();
        recyclerView.setAdapter(eventStreamAdapter);

        if (Mockups.isUserLoggedIn() && Mockups.getCurrentUser().getId() == event.getOwner()) {
            sendContainer.setVisibility(View.VISIBLE);
        }

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
                startActivity(CreateOrEditEvent.startEditMode(this, event.getId()));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    static class IncludedLayout1 {
        @BindView(R.id.event_header_user_name) TextView userName;
        @BindView(R.id.event_header_rate_user_up) ImageView rateUp;
        @BindView(R.id.event_header_rate_user_down) ImageView rateDown;
        @BindView(R.id.event_header_container) ViewGroup headerContainer;
    }

    static class IncludedLayout2 {
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
    }
}
