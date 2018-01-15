package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.fragments.profile.ProfileEventsFragment;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private final User user;

    public ProfilePagerAdapter(FragmentManager fm, User u) {
        super(fm);
        this.user = u;
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProfileEventsFragment.newInstance(user, ProfileEventsFragment.ProfileType.MYEVENTS);
            case 1:
                return ProfileEventsFragment.newInstance(user, ProfileEventsFragment.ProfileType.SUBSCRIBED);
            default:
                return null;
        }
    }

    @Override public int getCount() {
        return 2;
    }

    @Nullable @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "My events";
            case 1:
                return "My suscribes";
            default:
                return null;
        }
    }
}
