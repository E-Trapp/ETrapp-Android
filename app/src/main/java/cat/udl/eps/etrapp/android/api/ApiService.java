package cat.udl.eps.etrapp.android.api;

import java.util.List;

import cat.udl.eps.etrapp.android.models.Event;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("events.json")
    Call<List<Event>> listEvents();

    @GET("events/{id}.json")
    Call<Event> getEvent(@Path("id") long eventKey);

}
