package cat.udl.eps.etrapp.android.controllers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import cat.udl.eps.etrapp.android.api.ApiServiceManager;
import cat.udl.eps.etrapp.android.api.requests.SignInRequest;
import cat.udl.eps.etrapp.android.api.responses.ResponseUser;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.models.UserAuth;
import cat.udl.eps.etrapp.android.models.realm.CurrentUser;
import cat.udl.eps.etrapp.android.models.realm.TokenPersistence;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {

    private static UserController instance;

    private UserController() {
        //Empty Constructor
    }

    public static synchronized UserController getInstance() {
        if (instance == null)
            instance = new UserController();
        return instance;
    }

    public boolean isUserLoggedIn() {
        return Realm.getDefaultInstance().where(TokenPersistence.class)
                .equalTo("type", "sessionId")
                .findAll().size() > 0;
    }

    public String getCurrentToken() {
        final TokenPersistence tokenPersistence;
        if ((tokenPersistence = Realm.getDefaultInstance().where(TokenPersistence.class)
                .equalTo("type", "sessionId")
                .findFirst()) != null && isUserLoggedIn()) return tokenPersistence.getToken();
        return null;
    }

    public User getCurrentUser() {
        return User.current(Realm.getDefaultInstance().where(CurrentUser.class).findFirst());
    }

    public Task<String> authenticate(String username, String password) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.username = username;
        signInRequest.password = password;

        ApiServiceManager.getService().authenticateWithCredentials(signInRequest).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    final String token = response.body().getToken();

                    final TokenPersistence tokenPersistence = new TokenPersistence();
                    tokenPersistence.setToken(token);
                    tokenPersistence.setType("sessionId");

                    Realm.getDefaultInstance().executeTransaction(realm -> {
                        realm.copyToRealmOrUpdate(tokenPersistence);
                        realm.copyToRealmOrUpdate(CurrentUser.fromResponse(response.body()));
                    });

                    tcs.setResult(token);
                } else {
                    tcs.trySetException(new Exception("error"));
                }
            }

            @Override public void onFailure(Call<ResponseUser> call, Throwable t) {
                tcs.trySetException(new Exception(t.getMessage()));
            }
        });

        return tcs.getTask();
    }

    public Task<Void> deauthenticate() {
        final TaskCompletionSource<Void> tcs = new TaskCompletionSource<>();

        Realm.getDefaultInstance().executeTransactionAsync(realm -> {
            realm.delete(CurrentUser.class);
        });

        return tcs.getTask();
    }

    public Task<User> createUser(final UserAuth userAuth) {
        final TaskCompletionSource<User> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().createUser(userAuth).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {

                            final CurrentUser user = CurrentUser.fromUser(response.body());
                            Realm.getDefaultInstance().executeTransactionAsync(realm -> {
                                realm.copyToRealmOrUpdate(user);
                            });

                            tcs.trySetResult(User.current(user));
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        tcs.trySetException(new Exception(t.getCause()));
                    }
                }
        );

        return tcs.getTask();
    }

    public Task<User> getUserById(long owner) {
        TaskCompletionSource<User> tcs = new TaskCompletionSource<>();

        ApiServiceManager.getService().getUserById(owner).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                tcs.setResult(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                tcs.trySetException(new Exception(t.getMessage()));
            }
        });

        return tcs.getTask();
    }
}
