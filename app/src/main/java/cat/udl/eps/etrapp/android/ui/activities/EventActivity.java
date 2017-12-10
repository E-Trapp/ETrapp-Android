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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.FirebaseController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.adapters.EventStreamAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.ui.views.EndlessRecyclerOnScrollListener;
import cat.udl.eps.etrapp.android.utils.Toaster;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_EVENT_ID;
import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_EDIT_EVENT;

public class EventActivity extends BaseActivity {

    @BindView(R.id.event_stream_header) ViewGroup header;
    @BindView(R.id.event_stream_recycler) RecyclerView recyclerView;
    @BindView(R.id.event_stream_send_container) ViewGroup sendContainer;

    private Menu menu;
    private Event event;
    private EventStreamAdapter eventStreamAdapter;
    private TextView userName;
    private TextView created_date;
    private ImageView rateUp;
    private ImageView rateDown;
    private ImageView sendButton;
    private EditText sendText;

    public static Intent start(Context context, long eventKey) {
        Intent i = new Intent(context, EventActivity.class);
        i.putExtra(EXTRA_EVENT_ID, eventKey);
        return i;
    }

    @Override protected int getLayout() {
        return R.layout.activity_event;
    }

    @Override protected void configView() {
        eventStreamAdapter = new EventStreamAdapter();
        userName = header.findViewById(R.id.event_header_user_name);
        created_date = header.findViewById(R.id.event_header_created);
        rateUp = header.findViewById(R.id.event_header_rate_user_up);
        rateDown = header.findViewById(R.id.event_header_rate_user_down);
        sendButton = sendContainer.findViewById(R.id.event_stream_send_button);
        sendText = sendContainer.findViewById(R.id.event_stream_send_text);

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

        if (UserController.getInstance().isUserLoggedIn()) {
            if (UserController.getInstance().getCurrentUser().getId() == event.getOwner()) {
                menu.getItem(0).setVisible(true);
                sendContainer.setVisibility(View.VISIBLE);
                sendButton.setOnClickListener(view -> EventController.getInstance().writeMessage(event.getId(), sendText.getText().toString()));
            }
        }

        userName.setText(event.getTitle());
        created_date.setText(new Date(event.getStartsAt()).toString());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(eventStreamAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {

            boolean loading = false;

            @Override public void onScrolledToEnd() {
                if (!loading) {
                    if (eventStreamAdapter.getItemCount() >= 5) {
                        recyclerView.post(() -> {
                            loading = true;
                            FirebaseController.getInstance().getMessages(eventStreamAdapter.getLastKey(),event.getId(), eventStreamAdapter);
                        });
                    }
                }
                recyclerView.post(() -> loading = false);
            }
        });

        FirebaseController.getInstance().getMessages(null, event.getId(), eventStreamAdapter);

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
        @BindView(R.id.event_header_created) TextView created_date;
        @BindView(R.id.event_header_rate_user_up) ImageView rateUp;
        @BindView(R.id.event_header_rate_user_down) ImageView rateDown;
        @BindView(R.id.event_header_container) ViewGroup headerContainer;
    }

    static class IncludedLayout2 {
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
    }
}
