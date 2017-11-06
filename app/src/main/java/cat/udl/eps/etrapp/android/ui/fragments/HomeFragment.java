package cat.udl.eps.etrapp.android.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.activities.CreateOrEditEvent;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.adapters.HomeAdapter;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;
import cat.udl.eps.etrapp.android.utils.Mockups;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.ID_MENU_ITEM_CREATE_EVENT;

public class HomeFragment extends ScrollableFragment {


    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void configView(View fragmentView) {
        setHasOptionsMenu(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        HomeAdapter homeAdapter = new HomeAdapter(this);
        recyclerView.setAdapter(homeAdapter);
        homeAdapter.setOnClickListener(v -> {
            startActivity(EventActivity.start(getContext(), (long) v.getTag()));
        });
    }

    @Override
    public void scroll() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onPause() {
        super.onPause();

        for (Fragment f : getChildFragmentManager().getFragments()) {
            if (f instanceof FeaturedEventFragment) {
                Timber.d("Tag %s", f.getTag());
                getChildFragmentManager().beginTransaction().remove(f).commit();
            }
        }
    }

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (Mockups.isUserLoggedIn()) {
            menu.add(0, ID_MENU_ITEM_CREATE_EVENT, 50, R.string.add)
                    .setIcon(R.drawable.ic_plus_white_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ID_MENU_ITEM_CREATE_EVENT:
                startActivity(CreateOrEditEvent.startCreateMode(getContext()));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
