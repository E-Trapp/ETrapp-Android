package cat.udl.eps.etrapp.android.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.viewHolders.HomeContentViewHolder;
import cat.udl.eps.etrapp.android.ui.viewHolders.SearchResultViewHolder;
import cat.udl.eps.etrapp.android.utils.search.HighlightedResult;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultViewHolder> {

    private List<HighlightedResult> items;
    private View.OnClickListener clickListener;

    public void setItems(List<HighlightedResult> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result, parent, false));
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
        Log.d("tag","SearchResult");
    }

    @Override public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        HighlightedResult highlightedResult = items.get(position);
        final String title;

        // TODO fetch image from network;
        final String imageUrl;
        final String highlighted;

        SearchResultViewHolder viewHolder = (SearchResultViewHolder) holder;
        viewHolder.container.setOnClickListener(clickListener);

        //viewHolder.container.setTag(searchResult.getId());

        long searchId = 0;
        String type = null;
        Map<String, Object> data = new HashMap<>();
        if (highlightedResult.getResult() instanceof Event) {
            type = "event";
            searchId = ((Event)highlightedResult.getResult()).getId();
        } else if (highlightedResult.getResult() instanceof User) {
            type = "user";
            searchId = ((User)highlightedResult.getResult()).getId();
        }
        data.put("type", type);
        data.put("id", searchId);
        viewHolder.container.setTag(data);

        if (highlightedResult.getResult() instanceof Event) {
            Event e =  ((Event) highlightedResult.getResult());
            holder.search_result_image.setImageResource(R.drawable.event_placeholder);
            highlighted = "title";
            title = e.getTitle();
            imageUrl = e.getImageUrl();

        } else if (highlightedResult.getResult() instanceof User) {
            User user = ((User) highlightedResult.getResult());
            holder.search_result_image.setImageResource(R.drawable.profile_placeholder);
            title = user.getUsername();
            imageUrl = user.getAvatarUrl();
            highlighted = "username";
        } else {
            title = null;
            highlighted = null;
        }
        holder.search_result_title.setText(highlightResult(highlightedResult.getHighlight(highlighted).getHighlightedValue()));
    }

    @Override
    public int getItemCount() {
        return (items == null) ? 0 : items.size();
    }

    private SpannableStringBuilder highlightResult(String content) {

        SpannableStringBuilder s = new SpannableStringBuilder(content);

        String startTag = "<em>";
        int startTagLength = startTag.length();
        String endTag = "</em>";
        int endTagLength = endTag.length();
        Matcher m = Pattern.compile(startTag + "(.*?)" + endTag).matcher(content);
        final StyleSpan boldStyleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        while (m.find()) {
            s.setSpan(boldStyleSpan, m.start(1), m.end(1), 0);
            s.replace(m.end(1), m.end(1) + endTagLength, "");
            s.replace(m.start(1) - startTagLength, m.start(1), "");
        }

        return s;
    }
}
