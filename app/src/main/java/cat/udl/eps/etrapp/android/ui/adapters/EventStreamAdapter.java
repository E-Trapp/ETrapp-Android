package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.StreamMessage;
import cat.udl.eps.etrapp.android.ui.viewHolders.EventStreamViewHolder;

public class EventStreamAdapter extends RecyclerView.Adapter<EventStreamViewHolder> {

    private List<StreamMessage> streamMessageList;

    public void insertMessage(StreamMessage streamMessage) {
        if (streamMessageList == null) streamMessageList = new ArrayList<>();
        streamMessageList.add(streamMessage);
        notifyItemInserted(streamMessageList.size() - 1);
    }

    @Override public EventStreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventStreamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_event_message_stream, parent, false));
    }

    @Override public void onBindViewHolder(EventStreamViewHolder holder, int position) {
        StreamMessage streamMessage = streamMessageList.get(position);
        holder.content.setText(streamMessage.getMessage());
    }

    @Override public int getItemCount() {
        return (streamMessageList == null) ? 0 : streamMessageList.size();
    }
}
