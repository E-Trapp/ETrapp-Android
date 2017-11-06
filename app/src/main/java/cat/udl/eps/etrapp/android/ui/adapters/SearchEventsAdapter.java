package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.viewHolders.SearchResultViewHolder;


public class SearchEventsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private List<Event> items;

    public void setItems(List<Event> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        Event e = items.get(position);
        holder.search_result_image.setImageURI(e.getImageUrl());
        holder.search_result_title.setText(e.getTitle());
    }

    @Override
    public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }
}
