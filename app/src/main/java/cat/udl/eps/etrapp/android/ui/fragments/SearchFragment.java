package cat.udl.eps.etrapp.android.ui.fragments;

import android.view.View;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;

public class SearchFragment extends ScrollableFragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void configView(View fragmentView) {

    }

    @Override
    public void scroll() {

    }
}
