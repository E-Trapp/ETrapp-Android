package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.EventMessage;
import cat.udl.eps.etrapp.android.ui.viewHolders.EventStreamViewHolder;

public class EventStreamAdapter extends RecyclerView.Adapter<EventStreamViewHolder> {

    private List<EventMessage> items;

    private void addItemByTimestamp(EventMessage item) {
        int index;
        for (EventMessage alreadyInListItem : this.items) {
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

    public void addItem(EventMessage item) {
        if (this.items != null) {
            addItemByTimestamp(item);
        } else {
            this.items = new ArrayList<>();
            this.items.add(item);
            notifyItemInserted(0);
        }
    }

    @Override public EventStreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventStreamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event_message_stream, parent, false));
    }

    @Override public void onBindViewHolder(EventStreamViewHolder holder, int position) {
        EventMessage eventMessage = items.get(position);
        holder.content.setText(eventMessage.getMessage());
    }

    @Override public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }

    public String getLastKey() {
        if (items == null) return null;
        return items.get(items.size() - 1).getKey();
    }
}
