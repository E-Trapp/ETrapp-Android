package cat.udl.eps.etrapp.android.ui.viewHolders;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ButterizedViewHolder;

public class EventStreamViewHolder extends ButterizedViewHolder {

    @BindView(R.id.event_message_stream_content) public TextView content;
    @BindView(R.id.event_message_stream_time) public TextView time;

    public EventStreamViewHolder(View itemView) {
        super(itemView);
    }
}
