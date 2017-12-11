package cat.udl.eps.etrapp.android.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.ProfileController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.profile_followers_container) ViewGroup followers;
    @BindView(R.id.profile_following_container) ViewGroup following;
    @BindView(R.id.profile_user_image) SimpleDraweeView profilePicture;
    @BindView(R.id.userEventsText) TextView user_events_title;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private User user;

    public static Intent start(Context context, long owner) {
        return new Intent(context, UserProfileActivity.class)
                .putExtra("userKey", owner);
    }

    @Override protected int getLayout() {
        return R.layout.activity_user_profile;
    }

    @Override protected void configView() {
        handleIntent(getIntent());
        getCurrentActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUI() {
        getCurrentActionBar().setTitle(user.getUsername());
        ProfileController.newBuilder(new WeakReference<Activity>(this), this)
                .setFollowers(followers)
                .setFollowing(following)
                .setProfilePicture(profilePicture)
                .setUser_events_title(user_events_title)
                .setUser_followers_count(followers.findViewById(R.id.layout_follow_count))
                .setUser_followers_text(followers.findViewById(R.id.layout_follow_text))
                .setUser_following_count(following.findViewById(R.id.layout_follow_count))
                .setUser_following_text(following.findViewById(R.id.layout_follow_text))
                .setTheUser(user)
                .setRecyclerView(recyclerView)
                .build().load();
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("userKey")) {
                UserController.getInstance()
                        .getUserById(intent.getLongExtra("userKey", -1))
                        .addOnSuccessListener(result -> {
                            user = result;
                            setupUI();
                        });

            }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
