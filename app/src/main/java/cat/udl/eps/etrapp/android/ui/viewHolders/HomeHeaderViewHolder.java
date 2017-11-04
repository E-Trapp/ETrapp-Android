package cat.udl.eps.etrapp.android.ui.viewHolders;

import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ButterizedViewHolder;
import me.relex.circleindicator.CircleIndicator;

public class HomeHeaderViewHolder extends ButterizedViewHolder {

    @BindView(R.id.viewPager) public ViewPager viewPager;
    @BindView(R.id.indicator) public CircleIndicator indicator;

    public HomeHeaderViewHolder(View itemView) {
        super(itemView);
    }
}
