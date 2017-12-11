package cat.udl.eps.etrapp.android.controllers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.List;

import cat.udl.eps.etrapp.android.api.ApiServiceManager;
import cat.udl.eps.etrapp.android.api.requests.SendMessage;
import cat.udl.eps.etrapp.android.models.Event;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventController {

    private static EventController instance;

    private EventController() {
        //Empty Constructor
    }

    public static synchronized EventController getInstance() {
        if (instance == null)
            instance = new EventController();
        return instance;
    }


    public Task<List<Event>> getAllEvents() {
        final TaskCompletionSource<List<Event>> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().listEvents().enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                tcs.trySetResult(response.body());
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }

    public Task<Event> getEventById(long eventKey) {
        final TaskCompletionSource<Event> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().getEvent(eventKey).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.code() == 200) tcs.trySetResult(response.body());
                else tcs.trySetException(new Exception(response.message()));
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }

    public void writeMessage(long eventKey, String s) {
        ApiServiceManager.getService().writeMessage(eventKey, new SendMessage(s)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public Task<List<Event>> getUserEvents(long id) {
        final TaskCompletionSource<List<Event>> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().listUserEvents(id).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.isSuccessful()) {
                    tcs.setResult(response.body());
                } else {
                    tcs.trySetException(new Exception("Error"));
                }
            }

            @Override public void onFailure(Call<List<Event>> call, Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }
}
