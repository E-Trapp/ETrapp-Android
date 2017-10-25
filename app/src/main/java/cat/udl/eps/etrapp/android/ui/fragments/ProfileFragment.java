package cat.udl.eps.etrapp.android.ui.fragments;

import android.view.View;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void configView(View fragmentView) {

    }
}
