package cat.udl.eps.etrapp.android.ui.viewHolders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ButterizedViewHolder;

public class HomeContentViewHolder extends ButterizedViewHolder {

    @BindView(R.id.home_content_card) public ViewGroup container;
    @BindView(R.id.home_content_title) public TextView home_content_title;
    @BindView(R.id.home_content_date) public TextView home_content_date;
    @BindView(R.id.home_content_time) public TextView home_content_time;
    @BindView(R.id.home_content_score_progress) public ProgressBar home_content_progress;
    @BindView(R.id.home_content_score_likes) public TextView home_content_score_likes;
    @BindView(R.id.home_content_score_dislikes) public TextView home_content_score_dislikes;

    public HomeContentViewHolder(View itemView) {
        super(itemView);
    }
}
