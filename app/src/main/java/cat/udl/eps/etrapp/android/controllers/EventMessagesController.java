package cat.udl.eps.etrapp.android.controllers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.List;

import cat.udl.eps.etrapp.android.api.ApiServiceManager;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.EventMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventMessagesController {

    private static EventMessagesController instance;

    private EventMessagesController() {
        //Empty Constructor
    }

    public static synchronized EventMessagesController getInstance() {
        if (instance == null)
            instance = new EventMessagesController();
        return instance;
    }


    public Task<List<EventMessage>> getEventMessagesById(long eventKey) {
        final TaskCompletionSource<List<EventMessage>> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().getEventMessagesByEventId(eventKey).enqueue(new Callback<List<EventMessage>>() {
            @Override
            public void onResponse(Call<List<EventMessage>> call, Response<List<EventMessage>> response) {
                if (response.code() == 200) tcs.trySetResult(response.body());
                else tcs.trySetException(new Exception(response.message()));
            }

            @Override
            public void onFailure(Call<List<EventMessage>> call, Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }

}
