package cat.udl.eps.etrapp.android.ui.fragments;

import android.view.View;
import android.widget.Toast;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;

public class HomeFragment extends ScrollableFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void configView(View fragmentView) {

    }

    @Override
    public void scroll() {
        Toast.makeText(getContext(), "Reselected. Scrolling...", Toast.LENGTH_SHORT).show();
    }
}
