package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeContentViewHolder;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeHeaderViewHolder;
import cat.udl.eps.etrapp.android.utils.Mockups;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private final Fragment fragment;
    private List<Event> events; //Mockups.mockEventList;
    private List<Event> featuredEvents; //Mockups.mockFeaturedEventList;
    private View.OnClickListener clickListener;

    public HomeAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

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
        if (position == 0 && (featuredEvents != null && !featuredEvents.isEmpty())) {
            HomeHeaderViewHolder viewHolder = (HomeHeaderViewHolder) holder;
            if (featuredEvents.isEmpty()) {
                viewHolder.itemView.setVisibility(View.GONE);
            } else {
                viewHolder.viewPager.setAdapter(new HomeFragmentAdapter(fragment.getChildFragmentManager(), featuredEvents));
                viewHolder.indicator.setViewPager(viewHolder.viewPager);
            }
        } else {
                Event event = events.get(position);
                HomeContentViewHolder viewHolder = (HomeContentViewHolder) holder;
                viewHolder.container.setTag(event.getId());
                viewHolder.container.setOnClickListener(clickListener);
                viewHolder.home_content_title.setText(event.getTitle());

                System.out.println(event.toString());
                viewHolder.home_content_updated.setText(new Date(event.getCreated_at()).toString());
        }

    }

    @Override public int getItemViewType(int position) {
        if (position == 0 && (featuredEvents != null && !featuredEvents.isEmpty())) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }

    @Override public int getItemCount() {

        int featuredSize = 0;
        int eventsSize = 0;

        if (events != null) eventsSize = events.size();
        if (featuredEvents != null) featuredSize = featuredEvents.size();

        return featuredSize + eventsSize;
    }

    public void setFeaturedEvents(List<Event> featuredEvents) {
        this.featuredEvents = featuredEvents;
        notifyDataSetChanged();
    }

    public void setBothEvents(List<Event> feature, List<Event> normal) {
        this.events = normal;
        this.featuredEvents = feature;
        notifyDataSetChanged();
    }
}
