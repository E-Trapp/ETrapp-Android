package cat.udl.eps.etrapp.android.ui.fragments.event;

import android.os.Bundle;
import android.view.View;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;

public class CommentsFragment extends BaseFragment {

    public static CommentsFragment newInstance() {
        Bundle args = new Bundle();
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_comments;
    }

    @Override protected void configView(View fragmentView) {

    }
}
