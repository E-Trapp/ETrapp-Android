package cat.udl.eps.etrapp.android.ui.fragments;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.ProfileController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.ui.activities.MainActivity;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;
import cat.udl.eps.etrapp.android.utils.Toaster;

import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_SIGN_OUT;

public class ProfileFragment extends ScrollableFragment {

    @BindView(R.id.profile_followers_container) ViewGroup followers;
    @BindView(R.id.profile_following_container) ViewGroup following;
    @BindView(R.id.profile_user_image) SimpleDraweeView profilePicture;
    @BindView(R.id.userEventsText) TextView user_events_title;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.profile_floating_button) FloatingActionButton floatingActionButton;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void configView(View fragmentView) {
        setHasOptionsMenu(true);

        ProfileController.newBuilder(new WeakReference<Activity>(getActivity()), getContext())
                .setFloatingActionButton(floatingActionButton)
                .setFollowers(followers)
                .setFollowing(following)
                .setProfilePicture(profilePicture)
                .setUser_events_title(user_events_title)
                .setUser_followers_count(followers.findViewById(R.id.layout_follow_count))
                .setUser_followers_text(followers.findViewById(R.id.layout_follow_text))
                .setUser_following_count(following.findViewById(R.id.layout_follow_count))
                .setUser_following_text(following.findViewById(R.id.layout_follow_text))
                .setTheUser(UserController.getInstance().getCurrentUser())
                .setRecyclerView(recyclerView)
                .build().load();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, ID_MENU_ITEM_SIGN_OUT, 50, R.string.sign_out)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_ITEM_SIGN_OUT:
                UserController.getInstance()
                        .deauthenticate()
                        .addOnSuccessListener(aVoid -> {
                            Toaster.show(getContext(), R.string.sign_out_successful);
                            ((MainActivity)getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void scroll() {

    }
}
