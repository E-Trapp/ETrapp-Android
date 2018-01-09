package cat.udl.eps.etrapp.android.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.IndexQuery;
import com.algolia.search.saas.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.EventController;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.activities.EventActivity;
import cat.udl.eps.etrapp.android.ui.activities.UserProfileActivity;
import cat.udl.eps.etrapp.android.ui.adapters.HomeAdapter;
import cat.udl.eps.etrapp.android.ui.adapters.SearchResultsAdapter;
import cat.udl.eps.etrapp.android.ui.base.ScrollableFragment;
import cat.udl.eps.etrapp.android.ui.views.PaddingItemDecoration;
import cat.udl.eps.etrapp.android.utils.Toaster;
import cat.udl.eps.etrapp.android.utils.Utils;
import cat.udl.eps.etrapp.android.utils.search.HighlightedResult;
import cat.udl.eps.etrapp.android.utils.search.SearchResultsJsonParser;
import timber.log.Timber;

public class SearchFragment extends ScrollableFragment implements SearchView.OnQueryTextListener {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private static final String ALGOLIA_APP_ID = "DIECQMQ6FF";
    private static final String ALGOLIA_SEARCH_API_KEY = "1c7f6e0c12d5f1168b9b6097a0a1ea72";
    private static final String ALGOLIA_INDEX_NAME = "users";

    private SearchResultsJsonParser<User> userParser = new SearchResultsJsonParser<>(User.class);
    private SearchResultsJsonParser<Event> eventParser = new SearchResultsJsonParser<>(Event.class);

    private Client client = new Client(ALGOLIA_APP_ID, ALGOLIA_SEARCH_API_KEY);

    private SearchResultsAdapter searchResultsAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    /*
    @Override
    protected void configView(View fragmentView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        searchResultsAdapter = new SearchResultsAdapter();
        recyclerView.setAdapter(searchResultsAdapter);
    }
*/

    @Override
    protected void configView(View fragmentView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new PaddingItemDecoration(getContext()));
        searchResultsAdapter = new SearchResultsAdapter();
        recyclerView.setAdapter(searchResultsAdapter);

        searchResultsAdapter.setOnClickListener(v -> {

            Map<String, Object> data = (Map<String, Object>)v.getTag();

            String type = (String) data.get("type");
            long searchId = (long) data.get("id");

            if ("event".equals(type)) startActivity(EventActivity.start(getContext(), searchId));
            else if ("user".equals(type)) startActivity(UserProfileActivity.start(getContext(), searchId));

            Toaster.show(getContext(),"Item clicked");
        });


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
        //searchResultsAdapter.setItems(Mockups.mockEventList);
        Utils.hideIME(getContext(), getActivity().getCurrentFocus());
        return true;
    }

    @Override public boolean onQueryTextChange(String newText) {
        if (!newText.isEmpty()) {
            if (newText.contains("*") || newText.startsWith(" ")) {
                searchResultsAdapter.setItems(null);
                return false;
            }
            searchResultsAdapter.setItems(null);
            List<IndexQuery> queries = new ArrayList<>();
            queries.add(new IndexQuery("users", new Query(newText).setHitsPerPage(3)));
            queries.add(new IndexQuery("events", new Query(newText).setHitsPerPage(10)));
            final Client.MultipleQueriesStrategy strategy = Client.MultipleQueriesStrategy.NONE; // Execute the sequence of queries until the end.

            client.multipleQueriesAsync(queries, strategy, new CompletionHandler() {
                @Override
                public void requestCompleted(JSONObject content, AlgoliaException error) {
                    List<HighlightedResult> results = new ArrayList<>();
                    try {

                        //System.out.println(content);

                        results.addAll(eventParser.parseResults("title", content.getJSONArray("results").getJSONObject(1)));
                        results.addAll(userParser.parseResults("username", content.getJSONArray("results").getJSONObject(0)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    searchResultsAdapter.setItems(results);
                }
            });
            return true;
        } else {
            searchResultsAdapter.setItems(null);
            return false;
        }
    }
}
