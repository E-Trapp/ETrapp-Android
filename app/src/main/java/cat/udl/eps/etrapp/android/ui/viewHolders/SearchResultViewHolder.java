package cat.udl.eps.etrapp.android.ui.viewHolders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ButterizedViewHolder;


public class SearchResultViewHolder extends ButterizedViewHolder {

    @BindView(R.id.search_result_card) public ViewGroup container;
    @BindView(R.id.search_result_iv) public SimpleDraweeView search_result_image;
    @BindView(R.id.search_result_title) public TextView search_result_title;

    public SearchResultViewHolder(View itemView) {
        super(itemView);
    }
}
