package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Random;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Mockups;

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.profile_followers_container) ViewGroup followers;
    @BindView(R.id.profile_following_container) ViewGroup following;
    @BindView(R.id.profile_user_image) SimpleDraweeView profilePicture;
    @BindView(R.id.userEventsText) TextView userEventsText;

    private TextView user_followers_count;
    private TextView user_followers_text;
    private TextView user_following_count;
    private TextView user_following_text;

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

        user_followers_text = followers.findViewById(R.id.layout_follow_text);
        user_followers_count = followers.findViewById(R.id.layout_follow_count);
        user_following_text = following.findViewById(R.id.layout_follow_text);
        user_following_count = following.findViewById(R.id.layout_follow_count);

        user_following_text.setText(getString(R.string.following));
        user_followers_text.setText(getString(R.string.followers));

        user_following_count.setText("" + (Math.abs(new Random().nextInt() % 14522)));
        user_followers_count.setText("" + (Math.abs(new Random().nextInt() % 14522)));

        userEventsText.setText(String.format(getString(R.string.user_events), user.getUsername()));
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("userKey")) {
                user = Mockups.getUserById(intent.getLongExtra("userKey", -1));
                getCurrentActionBar().setTitle(user.getUsername());
            }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
