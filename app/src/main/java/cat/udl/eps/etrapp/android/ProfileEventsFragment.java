package cat.udl.eps.etrapp.android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.FirebaseController;
import cat.udl.eps.etrapp.android.controllers.SubscribeController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Subscribe;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.activities.UserProfileActivity;
import cat.udl.eps.etrapp.android.ui.adapters.EventStreamAdapter;
import cat.udl.eps.etrapp.android.ui.adapters.ProfileEventsAdapter;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.views.EndlessRecyclerOnScrollListener;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;
import cat.udl.eps.etrapp.android.utils.Toaster;
import cat.udl.eps.etrapp.android.utils.Utils;


public class ProfileEventsFragment extends BaseFragment {

    private static User user;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    public static ProfileEventsFragment newInstance(User u) {
        ProfileEventsFragment fragment = new ProfileEventsFragment();
        user = u;
        return fragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_profile_events;
    }

    @Override protected void configView(View fragmentView) {

        EventController.getInstance()
                .getUserEvents(user.getId())
                .addOnSuccessListener(events -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
                    ProfileEventsAdapter profileEventsAdapter = new ProfileEventsAdapter(events);
                    profileEventsAdapter.setOnClickListener(v -> {
                        this.startActivity(EventActivity.start(getContext(), (long) v.getTag()));
                    });
                    recyclerView.setAdapter(profileEventsAdapter);
                });

    }

}
