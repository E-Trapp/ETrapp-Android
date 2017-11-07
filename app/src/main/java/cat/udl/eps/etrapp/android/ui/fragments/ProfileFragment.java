package cat.udl.eps.etrapp.android.ui.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Random;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;

public class ProfileFragment extends ScrollableFragment {

    @BindView(R.id.profile_followers_container) ViewGroup followers;
    @BindView(R.id.profile_following_container) ViewGroup following;
    @BindView(R.id.profile_user_image) SimpleDraweeView profilePicture;
    @BindView(R.id.userEventsText) TextView userEventsText;

    private TextView user_followers_count;
    private TextView user_followers_text;
    private TextView user_following_count;
    private TextView user_following_text;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void configView(View fragmentView) {
        user_followers_text = followers.findViewById(R.id.layout_follow_text);
        user_followers_count = followers.findViewById(R.id.layout_follow_count);
        user_following_text = following.findViewById(R.id.layout_follow_text);
        user_following_count = following.findViewById(R.id.layout_follow_count);

        user_following_text.setText(getString(R.string.following));
        user_followers_text.setText(getString(R.string.followers));

        user_following_count.setText("" + (Math.abs(new Random().nextInt() % 14522)));
        user_followers_count.setText("" + (Math.abs(new Random().nextInt() % 14522)));

        userEventsText.setText(R.string.my_events);
    }

    @Override
    public void scroll() {

    }
}
