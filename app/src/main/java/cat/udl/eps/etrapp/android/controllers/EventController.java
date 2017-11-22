package cat.udl.eps.etrapp.android.controllers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.List;

import cat.udl.eps.etrapp.android.api.ApiServiceManager;
import cat.udl.eps.etrapp.android.models.Event;
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

}
