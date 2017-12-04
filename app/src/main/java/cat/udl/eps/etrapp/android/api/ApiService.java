package cat.udl.eps.etrapp.android.api;

import java.util.List;
import java.util.Map;

import cat.udl.eps.etrapp.android.api.requests.SignInRequest;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.models.UserAuth;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("events")
    Call<List<Event>> listEvents();

    @GET("events/{id}")
    Call<Event> getEvent(@Path("id") long eventKey);

    @POST("auth")
    Call<Map<String, String>> authenticateWithCredentials(@Body SignInRequest signInRequest);

    @GET("auth")
    Call<User> getCurrentUser();

    @POST("users")
    Call<User> createUser(@Body UserAuth userauth);

}
