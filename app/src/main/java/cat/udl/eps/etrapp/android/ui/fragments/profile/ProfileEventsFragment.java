package cat.udl.eps.etrapp.android.ui.fragments.profile;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.SubscribeController;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.adapters.ProfileEventsAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;


public class ProfileEventsFragment extends BaseFragment {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ProfileType type;
    private User user;

    public static ProfileEventsFragment newInstance(User u, ProfileType type) {
        ProfileEventsFragment fragment = new ProfileEventsFragment();
        fragment.user = u;
        fragment.type = type;
        return fragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_profile_events;
    }

    @Override protected void configView(View fragmentView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        ProfileEventsAdapter profileEventsAdapter = new ProfileEventsAdapter();
        profileEventsAdapter.setOnClickListener(v -> {
            this.startActivity(EventActivity.start(getContext(), (long) v.getTag()));
        });
        recyclerView.setAdapter(profileEventsAdapter);

        if (type == ProfileType.MYEVENTS) {
            EventController.getInstance()
                    .getUserEvents(user.getId())
                    .addOnSuccessListener(profileEventsAdapter::setItems);
        } else if (type == ProfileType.SUBSCRIBED) {
            SubscribeController.getInstance()
                    .getSubscribedEvents(user.getId())
                    .addOnSuccessListener(profileEventsAdapter::setItems);
        }
    }

    public enum ProfileType {
        MYEVENTS, SUBSCRIBED
    }
}
