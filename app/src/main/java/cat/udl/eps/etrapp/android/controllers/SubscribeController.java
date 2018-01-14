package cat.udl.eps.etrapp.android.controllers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.List;

import cat.udl.eps.etrapp.android.api.ApiServiceManager;
import cat.udl.eps.etrapp.android.api.requests.EventRequest;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.models.Subscribe;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by tan on 12/1/18.
 */

public class SubscribeController {
    private static SubscribeController instance;

    private SubscribeController() {
        //Empty Constructor
    }

    public static synchronized SubscribeController getInstance() {
        if (instance == null)
            instance = new SubscribeController();
        return instance;
    }

    public Task<Void> subscribeEvent(Subscribe subscribe) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().subscribeEvent(subscribe).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    tcs.setResult(null);
                } else {
                    tcs.setException(new Exception("Error: " + response.message()));
                }
            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                tcs.setException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }

    public Task<List<Event>> getUserEventsSubscribe(long id) {
        final TaskCompletionSource<List<Event>> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().getUserEventsSubscribe(id).enqueue(new Callback<List<Event>>() {
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
