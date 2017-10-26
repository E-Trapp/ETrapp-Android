package cat.udl.eps.etrapp.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;

public class SearchFragment extends ScrollableFragment implements SearchView.OnQueryTextListener {

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_open_search).expandActionView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_open_search).getActionView();

        if (searchView != null) {
            searchView.setQueryHint(getString(R.string.search_hint));
            searchView.setOnQueryTextListener(this);
            searchView.setIconifiedByDefault(false);
            searchView.requestFocus();
        }
    }

    @Override
    public void scroll() {

    }

    @Override public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override public boolean onQueryTextChange(String newText) {
        return false;
    }
}
