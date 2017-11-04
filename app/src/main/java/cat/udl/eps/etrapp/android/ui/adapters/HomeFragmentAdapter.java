package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.fragments.FeaturedEventFragment;

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Event> eventList;

    public HomeFragmentAdapter(FragmentManager fm, List<Event> eventList) {
        super(fm);
        this.eventList = eventList;
    }

    @Override public Fragment getItem(int position) {
        return FeaturedEventFragment.newInstance(eventList.get(position));
    }

    @Override public int getCount() {
        return 3;
    }
}
