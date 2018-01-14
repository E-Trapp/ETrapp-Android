package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cat.udl.eps.etrapp.android.ProfileEventsFragment;
import cat.udl.eps.etrapp.android.ProfileSubscribeFragment;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.fragments.event.CommentsFragment;
import cat.udl.eps.etrapp.android.ui.fragments.event.EventFragment;

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private final User user;

    public ProfilePagerAdapter(FragmentManager fm, User u) {
        super(fm);
        this.user = u;
    }

    @Override public Fragment getItem(int position) {
        System.out.println("Entrou aqui??" + position);
        switch (position) {
            case 0: return ProfileEventsFragment.newInstance(user);
            case 1: return ProfileSubscribeFragment.newInstance(user);
            default: return null;
        }
    }

    @Override public int getCount() {
        return 2;
    }

    @Nullable @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "My events";
            case 1: return "My suscribes";
            default: return null;
        }
    }
}
