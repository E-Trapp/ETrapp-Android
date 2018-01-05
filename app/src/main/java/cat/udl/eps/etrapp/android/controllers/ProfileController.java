package cat.udl.eps.etrapp.android.controllers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;
import java.util.Random;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.activities.RegisterActivity;
import cat.udl.eps.etrapp.android.ui.adapters.ProfileEventsAdapter;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;

public class ProfileController {

    private final WeakReference<? extends Activity> activity;
    private final Context context;

    private final ViewGroup followers;
    private final ViewGroup following;
    private final SimpleDraweeView profilePicture;

    private final RecyclerView recyclerView;

    private final TextView user_events_title;
    private final TextView user_followers_count;
    private final TextView user_followers_text;
    private final TextView user_following_count;
    private final TextView user_following_text;
    private final TextView user_name;

    private final FloatingActionButton floatingActionButton;

    private final User theUser;

    private ProfileController(Builder builder) {
        context = builder.context;
        activity = builder.activity;
        floatingActionButton = builder.floatingActionButton;
        followers = builder.followers;
        following = builder.following;
        profilePicture = builder.profilePicture;
        recyclerView = builder.recyclerView;
        user_events_title = builder.user_events_title;
        user_followers_count = builder.user_followers_count;
        user_followers_text = builder.user_followers_text;
        user_following_count = builder.user_following_count;
        user_following_text = builder.user_following_text;
        user_name = builder.user_name;
        theUser = builder.theUser;
    }

    public static Builder newBuilder(WeakReference<? extends Activity> activity, Context context) {
        return new Builder(activity, context);
    }

    public void load() {
        loadEvents();
        user_following_text.setText(context.getString(R.string.following));
        user_followers_text.setText(context.getString(R.string.followers));

        user_following_count.setText("" + (Math.abs(new Random().nextInt() % 14522)));
        user_followers_count.setText("" + (Math.abs(new Random().nextInt() % 14522)));

        if (theUser.getFirstName() == null && theUser.getLastName() == null) {
            user_name.setText(theUser.getUsername());
        } else {
            user_name.setText(String.format("%s %s",
                    theUser.getFirstName() != null ? theUser.getFirstName() : "",
                    theUser.getLastName() != null ? theUser.getLastName() : ""));
        }

        if (UserController.getInstance().isCurrentUser(theUser)) {
            user_events_title.setText(R.string.my_events);
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(view -> activity.get().startActivity(RegisterActivity.edit(context, UserController.getInstance().getCurrentUser().getId())));
        } else {
            user_events_title.setText(String.format(context.getString(R.string.user_events), theUser.getUsername()));
        }
    }

    private void loadEvents() {
        EventController.getInstance()
                .getUserEvents(theUser.getId())
                .addOnSuccessListener(events -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    recyclerView.addItemDecoration(new PaddingItemDecoration(context));
                    ProfileEventsAdapter profileEventsAdapter = new ProfileEventsAdapter(events);
                    profileEventsAdapter.setOnClickListener(v -> {
                        activity.get().startActivity(EventActivity.start(context, (long) v.getTag()));
                    });
                    recyclerView.setAdapter(profileEventsAdapter);
                });
    }


    public static final class Builder {
        private final Context context;
        private final WeakReference<? extends Activity> activity;
        private FloatingActionButton floatingActionButton;
        private ViewGroup followers;
        private ViewGroup following;
        private SimpleDraweeView profilePicture;
        private RecyclerView recyclerView;
        private TextView user_events_title;
        private TextView user_followers_count;
        private TextView user_followers_text;
        private TextView user_following_count;
        private TextView user_following_text;
        private TextView user_name;
        private User theUser;

        private Builder(WeakReference<? extends Activity> activity, Context context) {
            this.activity = activity;
            this.context = context;
        }

        public Builder setFloatingActionButton(FloatingActionButton floatingActionButton) {
            this.floatingActionButton = floatingActionButton;
            return this;
        }

        public Builder setFollowers(ViewGroup followers) {
            this.followers = followers;
            return this;
        }

        public Builder setFollowing(ViewGroup following) {
            this.following = following;
            return this;
        }

        public Builder setProfilePicture(SimpleDraweeView profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public Builder setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder setUser_events_title(TextView user_events_title) {
            this.user_events_title = user_events_title;
            return this;
        }

        public Builder setUser_followers_count(TextView user_followers_count) {
            this.user_followers_count = user_followers_count;
            return this;
        }

        public Builder setUser_followers_text(TextView user_followers_text) {
            this.user_followers_text = user_followers_text;
            return this;
        }

        public Builder setUser_following_count(TextView user_following_count) {
            this.user_following_count = user_following_count;
            return this;
        }

        public Builder setUser_following_text(TextView user_following_text) {
            this.user_following_text = user_following_text;
            return this;
        }

        public Builder setUser_name(TextView user_name) {
            this.user_name = user_name;
            return this;
        }

        public Builder setTheUser(User theUser) {
            this.theUser = theUser;
            return this;
        }

        public ProfileController build() {
            return new ProfileController(this);
        }
    }
}
