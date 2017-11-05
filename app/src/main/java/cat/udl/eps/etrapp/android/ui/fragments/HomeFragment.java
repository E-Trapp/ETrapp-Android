package cat.udl.eps.etrapp.android.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.adapters.HomeAdapter;
import cat.udl.eps.etrapp.android.ui.adapters.SearchEventsAdapter;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;
import timber.log.Timber;

public class HomeFragment extends ScrollableFragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void configView(View fragmentView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        HomeAdapter homeAdapter = new HomeAdapter(this);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnClickListener(v -> {
            startActivity(EventActivity.start(getContext(), (long)v.getTag()));
        });
    }

    @Override
    public void scroll() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onPause() {
        super.onPause();

        for (Fragment f : getChildFragmentManager().getFragments() ) {
            if (f instanceof FeaturedEventFragment ) {
                Timber.d("Tag %s", f.getTag());
                getChildFragmentManager().beginTransaction().remove(f).commit();
            }
        }

    }
}
