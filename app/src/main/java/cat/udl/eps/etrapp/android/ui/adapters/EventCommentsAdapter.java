package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.EventComment;
import cat.udl.eps.etrapp.android.ui.viewHolders.EventCommentsViewHolder;

public class EventCommentsAdapter extends RecyclerView.Adapter<EventCommentsViewHolder> {

    private List<EventComment> items;

    private SimpleDateFormat hours = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat before = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private void addItemByTimestamp(EventComment item) {
        int index;
        for (EventComment alreadyInListItem : this.items) {
            if (item.getTimestamp() > alreadyInListItem.getTimestamp()) {
                index = this.items.indexOf(alreadyInListItem);
                this.items.add(index, item);
                notifyItemInserted(index);
                return;
            }
        }

        this.items.add(item);
        notifyItemInserted(this.items.size() - 1);
    }

    public void addItem(EventComment item) {
        if (this.items != null) {
            if (this.items.contains(item)) {
                replaceData(item, this.items.indexOf(item));
            } else addItemByTimestamp(item);
        } else {
            this.items = new ArrayList<>();
            this.items.add(item);
            notifyItemInserted(0);
        }
    }

    private void replaceData(EventComment item, int index) {
        if (!this.items.get(index).equals(item)) {
            this.items.remove(index);
            this.items.add(index, item);
            notifyItemChanged(index);
        }
    }

    @Override public EventCommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventCommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event_comments, parent, false));
    }

    @Override public void onBindViewHolder(EventCommentsViewHolder holder, int position) {
        EventComment eventMessage = items.get(position);
        holder.content.setText(eventMessage.getComment());
        holder.time.setText(getDateFromLong(eventMessage.getTimestamp()));
        UserController.getInstance()
                .getUserById(eventMessage.getUserId())
                .addOnSuccessListener(user -> {
                    holder.user.setText(user.getUsername());
                });
    }

    private String getDateFromLong(long timestamp) {
        final String date;
        Date messageDate = new Date(timestamp);
        if (DateUtils.isToday(timestamp)) {
            date = hours.format(messageDate);
        } else {
            date = before.format(messageDate);
        }
        return date;
    }

    @Override public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }

    public String getLastKey() {
        if (items == null) return null;
        return items.get(items.size() - 1).getKey();
    }
}
