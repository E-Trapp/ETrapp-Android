package cat.udl.eps.etrapp.android.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class ButterizedViewHolder extends RecyclerView.ViewHolder {

    public ButterizedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
