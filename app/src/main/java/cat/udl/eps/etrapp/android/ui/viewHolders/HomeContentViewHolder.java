package cat.udl.eps.etrapp.android.ui.viewHolders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ButterizedViewHolder;

public class HomeContentViewHolder extends ButterizedViewHolder {

    @BindView(R.id.home_content_card) public ViewGroup container;
    @BindView(R.id.home_content_iv) public SimpleDraweeView home_content_image;
    @BindView(R.id.home_content_title) public TextView home_content_title;

    public HomeContentViewHolder(View itemView) {
        super(itemView);
    }
}
