package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.tasks.OnFailureListener;

import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeContentViewHolder;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeHeaderViewHolder;
import timber.log.Timber;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

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

    public void setOnClickListener(View.OnClickListener listener) {this.clickListener = listener;}

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
                viewHolder.home_content_date.setText(date.format(event.getStartsAt()));
                viewHolder.home_content_time.setText(time.format(event.getStartsAt()));
                EventController.getInstance()
                        .getScores(event.getId())
                        .addOnSuccessListener(scores -> {
                            viewHolder.home_content_score_likes.setText(String.valueOf(scores.get("likes")));
                            viewHolder.home_content_score_dislikes.setText(String.valueOf(scores.get("dislikes")));
                            viewHolder.home_content_progress.setProgress(scores.get("score").intValue());
                        })
                .addOnFailureListener(e -> {
                    viewHolder.home_content_score_likes.setText(String.valueOf(0));
                    viewHolder.home_content_score_dislikes.setText(String.valueOf(0));
                    viewHolder.home_content_progress.setProgress(0);
                });
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
