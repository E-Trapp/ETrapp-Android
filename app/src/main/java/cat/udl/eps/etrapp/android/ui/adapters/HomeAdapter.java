package cat.udl.eps.etrapp.android.ui.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeContentViewHolder;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeHeaderViewHolder;
import cat.udl.eps.etrapp.android.utils.Mockups;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private final Fragment fragment;

    public HomeAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    private List<Event> events = Mockups.mockEventList;
    private List<Event> featuredEvents = Mockups.mockFeaturedEventList;

    private View.OnClickListener clickListener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_HEADER) {
            return new HomeHeaderViewHolder(inflater.inflate(R.layout.pager_home_screen, parent, false));
        } else {
            return new HomeContentViewHolder(inflater.inflate(R.layout.row_home_content, parent, false));
        }
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            HomeHeaderViewHolder viewHolder = (HomeHeaderViewHolder)holder;
            viewHolder.viewPager.setAdapter(new HomeFragmentAdapter(fragment.getChildFragmentManager(), featuredEvents));
            viewHolder.indicator.setViewPager(viewHolder.viewPager);

        } else {
            Event event = events.get(position - 1);
            HomeContentViewHolder viewHolder = (HomeContentViewHolder)holder;
            viewHolder.container.setTag(event.getId());
            viewHolder.container.setOnClickListener(clickListener);
        }
    }

    @Override public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }

    @Override public int getItemCount() {
        return events.size() + 1;
    }
}
