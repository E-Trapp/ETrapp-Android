package cat.udl.eps.etrapp.android.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.adapters.SearchEventsAdapter;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;
import cat.udl.eps.etrapp.android.utils.Mockups;
import cat.udl.eps.etrapp.android.utils.Utils;

public class SearchFragment extends ScrollableFragment implements SearchView.OnQueryTextListener {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private SearchEventsAdapter searchEventsAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void configView(View fragmentView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        searchEventsAdapter = new SearchEventsAdapter();
        recyclerView.setAdapter(searchEventsAdapter);
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
        recyclerView.smoothScrollToPosition(0);
    }

    @Override public boolean onQueryTextSubmit(String query) {
        searchEventsAdapter.setItems(Mockups.mockEventList);
        Utils.hideIME(getContext(), getActivity().getCurrentFocus());
        return true;
    }

    @Override public boolean onQueryTextChange(String newText) {
        return false;
    }
}
