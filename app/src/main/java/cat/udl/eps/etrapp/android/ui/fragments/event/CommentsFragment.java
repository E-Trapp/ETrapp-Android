package cat.udl.eps.etrapp.android.ui.fragments.event;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.FirebaseController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.adapters.EventCommentsAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.views.EndlessRecyclerOnScrollListener;
import cat.udl.eps.etrapp.android.utils.Toaster;
import cat.udl.eps.etrapp.android.utils.Utils;

public class CommentsFragment extends BaseFragment {

    public static CommentsFragment newInstance(Event e) {
        CommentsFragment fragment = new CommentsFragment();
        fragment.event = e;
        return fragment;
    }

    @BindView(R.id.event_comment_recycler) RecyclerView recyclerView;
    @BindView(R.id.event_comment_send_container) ViewGroup sendContainer;

    private Event event;

    private EventCommentsAdapter eventCommentsAdapter;

    private ImageView sendButton;
    private EditText sendText;

    @Override protected int getLayout() {
        return R.layout.fragment_comments;
    }

    @Override protected void configView(View fragmentView) {
        eventCommentsAdapter = new EventCommentsAdapter();
        sendButton = sendContainer.findViewById(R.id.event_comment_send_button);
        sendText = sendContainer.findViewById(R.id.event_comment_send_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(eventCommentsAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {

            boolean loading = false;

            @Override public void onScrolledToEnd() {
                if (!loading) {
                    if (eventCommentsAdapter.getItemCount() >= 5) {
                        recyclerView.post(() -> {
                            loading = true;
                            FirebaseController.getInstance().getComments(eventCommentsAdapter.getLastKey(), event.getId(), eventCommentsAdapter);
                        });
                    }
                }
                recyclerView.post(() -> loading = false);
            }
        });

        FirebaseController.getInstance().getComments(null, event.getId(), eventCommentsAdapter);

        if (UserController.getInstance().isUserLoggedIn()) {
            sendContainer.setVisibility(View.VISIBLE);
            sendButton.setOnClickListener(view -> EventController.getInstance()
                    .writeComment(event.getId(), sendText.getText().toString())
                    .addOnSuccessListener(aVoid -> {
                        sendText.setText("");
                        Utils.hideIME(getContext(), sendText);
                        Toaster.show(getContext(), "Comment added");
                    }));
        }
    }

    static class IncludedLayout {
        @BindView(R.id.recyclerView) RecyclerView recyclerView;
    }
}
