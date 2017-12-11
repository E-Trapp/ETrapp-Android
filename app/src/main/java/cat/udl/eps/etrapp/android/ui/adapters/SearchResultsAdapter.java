package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.viewHolders.SearchResultViewHolder;
import cat.udl.eps.etrapp.android.utils.search.HighlightedResult;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private List<HighlightedResult> items;

    public void setItems(List<HighlightedResult> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result, parent, false));
    }

    @Override public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        HighlightedResult e = items.get(position);
        if (e.getResult() instanceof Event) {
            holder.search_result_image.setImageResource(R.drawable.event_placeholder);
            holder.search_result_title.setText(((Event) e.getResult()).getTitle());
        } else if (e.getResult() instanceof User) {
            holder.search_result_image.setImageResource(R.drawable.profile_placeholder);
            holder.search_result_title.setText(((User) e.getResult()).getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }
}
