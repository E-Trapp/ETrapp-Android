package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeContentViewHolder;


public class ProfileEventsAdapter extends RecyclerView.Adapter<HomeContentViewHolder> {

    private SimpleDateFormat time = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    private List<Event> eventList;

    private View.OnClickListener clickListener;

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public HomeContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_content, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeContentViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.container.setTag(event.getId());
        holder.container.setOnClickListener(clickListener);
        holder.home_content_title.setText(event.getTitle());
        holder.home_content_date.setText(date.format(event.getStartsAt()));
        holder.home_content_time.setText(time.format(event.getStartsAt()));
        EventController.getInstance()
                .getScores(event.getId())
                .addOnSuccessListener(scores -> {
                    holder.home_content_score_likes.setText(String.valueOf(scores.get("likes")));
                    holder.home_content_score_dislikes.setText(String.valueOf(scores.get("dislikes")));
                    holder.home_content_progress.setProgress(scores.get("score").intValue());
                })
                .addOnFailureListener(e -> {
                    holder.home_content_score_likes.setText(String.valueOf(0));
                    holder.home_content_score_dislikes.setText(String.valueOf(0));
                    holder.home_content_progress.setProgress(0);
                });
    }

    @Override
    public int getItemCount() {
        return (eventList != null) ? eventList.size() : 0;
    }

    public void setItems(List<Event> eventList) {
        this.eventList = eventList;
        notifyDataSetChanged();
    }
}
