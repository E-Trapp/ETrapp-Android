package cat.udl.eps.etrapp.android.ui.fragments.event;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import cat.udl.eps.etrapp.android.ui.activities.UserProfileActivity;
import cat.udl.eps.etrapp.android.ui.adapters.EventStreamAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.views.EndlessRecyclerOnScrollListener;
import cat.udl.eps.etrapp.android.utils.Toaster;
import cat.udl.eps.etrapp.android.utils.Utils;


public class EventFragment extends BaseFragment {

    @BindView(R.id.event_stream_header) ViewGroup header;
    @BindView(R.id.event_stream_recycler) RecyclerView recyclerView;
    @BindView(R.id.event_stream_send_container) ViewGroup sendContainer;
    private Event event;
    private EventStreamAdapter eventStreamAdapter;
    private TextView userName;
    private TextView created_date;
    private TextView location;
    private TextView description;
    private ImageView rateUp;
    private ImageView rateDown;
    private ImageView sendButton;
    private EditText sendText;

    public static EventFragment newInstance(Event e) {
        EventFragment fragment = new EventFragment();
        fragment.event = e;
        return fragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_event;
    }

    @Override protected void configView(View fragmentView) {
        eventStreamAdapter = new EventStreamAdapter();
        userName = header.findViewById(R.id.event_header_user_name);
        created_date = header.findViewById(R.id.event_header_created);
        rateUp = header.findViewById(R.id.event_header_rate_user_up);
        rateDown = header.findViewById(R.id.event_header_rate_user_down);
        location = header.findViewById(R.id.event_header_location);
        description = header.findViewById(R.id.event_header_description);

        sendButton = sendContainer.findViewById(R.id.event_stream_send_button);
        sendText = sendContainer.findViewById(R.id.event_stream_send_text);

        View.OnClickListener clickListener = view -> {
            switch (view.getId()) {
                case R.id.event_stream_header:
                    startActivity(UserProfileActivity.start(getContext(), event.getOwner()));
                    break;
                case R.id.event_header_rate_user_up:
                    Toaster.show(getContext(), "ETrapper Upvoted!");
                    break;
                case R.id.event_header_rate_user_down:
                    Toaster.show(getContext(), "ETrapper Downvoted!");
                    break;
            }
        };


        header.setOnClickListener(clickListener);
        rateUp.setOnClickListener(clickListener);
        rateDown.setOnClickListener(clickListener);

        setupUI();
    }

    private void setupUI() {

        if (UserController.getInstance().isUserLoggedIn()) {
            if (UserController.getInstance().getCurrentUser().getId() == event.getOwner()) {
                sendContainer.setVisibility(View.VISIBLE);
                sendButton.setOnClickListener(view -> {
                    EventController.getInstance()
                            .writeMessage(event.getId(), sendText.getText().toString())
                            .addOnSuccessListener(aVoid -> {
                                sendText.setText("");
                                Utils.hideIME(getContext(), sendText);
                            });
                });
            }
        }

        UserController.getInstance()
                .getUserById(event.getOwner())
                .addOnSuccessListener(user -> {
                    userName.setText(user.getUsername());
                });
        created_date.setText(new Date(event.getStartsAt()).toString());
        description.setText(event.getDescription());
        location.setText(event.getLocation());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
                            FirebaseController.getInstance().getMessages(eventStreamAdapter.getLastKey(), event.getId(), eventStreamAdapter);
                        });
                    }
                }
                recyclerView.post(() -> loading = false);
            }
        });

        FirebaseController.getInstance().getMessages(null, event.getId(), eventStreamAdapter);
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
