package cat.udl.eps.etrapp.android.ui.viewHolders;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ButterizedViewHolder;

public class EventCommentsViewHolder extends ButterizedViewHolder {

    @BindView(R.id.event_comments_content) public TextView content;
    @BindView(R.id.event_comments_time) public TextView time;
    @BindView(R.id.event_comments_user) public TextView user;

    public EventCommentsViewHolder(View itemView) {
        super(itemView);
    }

}
