package cat.udl.eps.etrapp.android.controllers;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cat.udl.eps.etrapp.android.api.ApiServiceManager;
import cat.udl.eps.etrapp.android.api.requests.EventRequest;
import cat.udl.eps.etrapp.android.api.requests.SendComment;
import cat.udl.eps.etrapp.android.api.requests.SendMessage;
import cat.udl.eps.etrapp.android.models.Event;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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

        ApiServiceManager.getService().listEvents(null).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                if (response.isSuccessful()) tcs.trySetResult(response.body());
                else tcs.trySetException(new Exception());
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }

    public Task<Event> getEventById(long eventKey) {
        final TaskCompletionSource<Event> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().getEvent(eventKey).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
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

    public Task<Void> writeMessage(long eventKey, String s) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        ApiServiceManager.getService().writeMessage(eventKey, new SendMessage(s)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    tcs.setResult(null);
                } else {
                    tcs.setException(new Exception("Error"));
                }
            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                tcs.setException(new Exception(t.getCause()));
            }
        });
        return tcs.getTask();
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

    public Task<Void> editEvent(long id, Map<String, Object> updates) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().editEvent(id, updates).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                tcs.setResult(null);
            }

            @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                tcs.setException(new Exception(t.getCause()));
            }
        });


        return tcs.getTask();
    }

    public Task<Void> createEvent(Event event) {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().createEvent(EventRequest.fromEvent(event)).enqueue(new Callback<ResponseBody>() {
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

    public Task<Void> writeComment(long eventKey, String s) {

        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();
        ApiServiceManager.getService().writeComment(eventKey, new SendComment(s)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    tcs.setResult(null);
                } else {
                    tcs.setException(new Exception("Error"));
                }
            }

            @Override public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                tcs.setException(new Exception(t.getCause()));
            }
        });
        return tcs.getTask();
    }

    public Task<List<Event>> getEventsFromCategory(long id) {
        final TaskCompletionSource<List<Event>> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().listEvents(id).enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                if (response.isSuccessful()) tcs.trySetResult(response.body());
                else tcs.trySetException(new Exception());
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }

    public Task<Map<String, Long>> getScores(long id) {
        final TaskCompletionSource<Map<String, Long>> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().getScores(id).enqueue(new Callback<Map<String, Long>>() {
            @Override
            public void onResponse(Call<Map<String, Long>> call, Response<Map<String, Long>> response) {
                if (response.isSuccessful()) {
                    tcs.trySetResult(response.body());
                }                 else tcs.trySetException(new Exception());

            }

            @Override
            public void onFailure(Call<Map<String, Long>> call, Throwable t) {
                tcs.trySetException(new Exception(t.getCause()));
            }
        });

        return tcs.getTask();
    }
}
