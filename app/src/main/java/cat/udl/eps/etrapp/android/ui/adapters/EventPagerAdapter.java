package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.fragments.event.CommentsFragment;
import cat.udl.eps.etrapp.android.ui.fragments.event.EventFragment;

public class EventPagerAdapter extends FragmentStatePagerAdapter {

    private final Event e;

    public EventPagerAdapter(FragmentManager fm, Event e) {
        super(fm);
        this.e = e;
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0: return EventFragment.newInstance(e);
            case 1: return CommentsFragment.newInstance(e);
            default: return null;
        }
    }

    @Override public int getCount() {
        return 2;
    }

    @Nullable @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Messages";
            case 1: return "Comments";
            default: return null;
        }
    }
}
