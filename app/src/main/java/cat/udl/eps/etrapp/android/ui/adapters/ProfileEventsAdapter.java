package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Date;
import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeContentViewHolder;

/**
 * Created by ry on 11/12/2017.
 */

public class ProfileEventsAdapter extends RecyclerView.Adapter<HomeContentViewHolder>{

    public ProfileEventsAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    private final List<Event> eventList;

    private View.OnClickListener clickListener;

    public void setOnClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override public HomeContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_content, parent, false));
    }

    @Override public void onBindViewHolder(HomeContentViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.container.setTag(event.getId());
        holder.container.setOnClickListener(clickListener);
        holder.home_content_title.setText(event.getTitle());
        holder.home_content_updated.setText(new Date(event.getStartsAt()).toString());
        /*
        UserController.getInstance().getUserById(event.getOwner()).addOnSuccessListener(user -> {
            holder.home_content_owner.setText(user.getUsername());
        });
        */
    }

    @Override public int getItemCount() {
        return eventList.size();
    }
}
