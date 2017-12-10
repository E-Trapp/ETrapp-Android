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

    private List<EventMessage> eventMessageList;

    public void swap(List<EventMessage> newEventList)
    {
        if(newEventList == null || newEventList.size()==0)
            return;
        if (eventMessageList != null && eventMessageList.size()>0)
            eventMessageList.clear();
        eventMessageList.addAll(newEventList);
        notifyDataSetChanged();
    }

    public void insertMessage(EventMessage eventMessage) {
        if (eventMessageList == null) eventMessageList = new ArrayList<>();
        eventMessageList.add(eventMessage);
        notifyItemInserted(eventMessageList.size() - 1);
    }

    @Override public EventStreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventStreamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event_message_stream, parent, false));
    }

    @Override public void onBindViewHolder(EventStreamViewHolder holder, int position) {
        EventMessage eventMessage = eventMessageList.get(position);
        holder.content.setText(eventMessage.getMessage());
    }

    @Override public int getItemCount() {
        return (eventMessageList == null) ? 0 : eventMessageList.size();
    }
}
