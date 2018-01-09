/*
 * Copyright (c) 2015 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cat.udl.eps.etrapp.android.utils.search;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.User;
import timber.log.Timber;

/**
 * Parses the JSON output of a search query.
 */
public class SearchResultsJsonParser<T> {

    private final Class<T> klass;

    public SearchResultsJsonParser(Class<T> klass) {
        this.klass = klass;
    }

    /**
     * Parse the root result JSON object into a list of results.
     *
     * @param jsonObject The result's root object.
     * @return A list of results (potentially empty), or null in case of error.
     */
    public List<HighlightedResult> parseResults(String param, JSONObject jsonObject) {
        if (jsonObject == null)
            return null;


        Timber.d(jsonObject.toString());

        List<HighlightedResult> results = new ArrayList<>();
        JSONArray hits = jsonObject.optJSONArray("hits");
        Timber.d(jsonObject.toString());
        if (hits == null)
            return null;

        for (int i = 0; i < hits.length(); ++i) {
            JSONObject hit = hits.optJSONObject(i);
            if (hit == null)
                continue;

            T item = new Gson().fromJson(hit.toString(), klass);


            if (item == null)
                continue;

            if (UserController.getInstance().isUserLoggedIn())
                if (item instanceof User && ((User) item).getId() == UserController.getInstance().getCurrentUser().getId())
                    continue;

            JSONObject highlightResult = hit.optJSONObject("_highlightResult");
            if (highlightResult == null)
                continue;

            JSONObject highlightTitle = highlightResult.optJSONObject(param);
            if (highlightTitle == null)
                continue;

            String value = highlightTitle.optString("value");
            if (value == null)
                continue;
            HighlightedResult<T> result = new HighlightedResult<>(item);
            result.addHighlight(param, new Highlight(param, value));
            results.add(result);
        }
        return results;
    }
}
