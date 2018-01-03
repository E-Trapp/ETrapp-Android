package cat.udl.eps.etrapp.android.api;

import java.util.List;
import java.util.Map;

import cat.udl.eps.etrapp.android.api.requests.EventRequest;
import cat.udl.eps.etrapp.android.api.requests.SendComment;
import cat.udl.eps.etrapp.android.api.requests.SendMessage;
import cat.udl.eps.etrapp.android.api.requests.SignInRequest;
import cat.udl.eps.etrapp.android.api.requests.TokenInfo;
import cat.udl.eps.etrapp.android.api.responses.ResponseUser;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.models.UserAuth;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("events")
    Call<List<Event>> listEvents();

    @POST("events")
    Call<ResponseBody> createEvent(@Body EventRequest event);

    @GET("events/{id}")
    Call<Event> getEvent(@Path("id") long eventKey);

    @PATCH("events/{id}")
    Call<ResponseBody> editEvent(@Path("id") long eventKey, @Body Map<String, Object> updates);

    @POST("events/{id}/messages")
    Call<ResponseBody> writeMessage(@Path("id") long eventKey, @Body SendMessage eventMessage);

    @POST("events/{id}/comments")
    Call<ResponseBody> writeComment(@Path("id") long eventKey, @Body SendComment eventComment);

    @POST("auth")
    Call<ResponseUser> authenticateWithCredentials(@Body SignInRequest signInRequest);

    @GET("auth")
    Call<User> getCurrentUser();

    @DELETE("auth")
    Call<ResponseBody> signOut();

    @POST("users")
    Call<User> createUser(@Body UserAuth userauth);

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") long ownerKey);

    @PUT("users/{id}/token")
    Call<ResponseBody> updateToken(@Path("id") long userKey, @Body TokenInfo tokenInfo);

    @GET("users/{id}/events")
    Call<List<Event>> listUserEvents(@Path("id") long userKey);


}
