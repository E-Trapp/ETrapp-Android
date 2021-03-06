package cat.udl.eps.etrapp.android.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import cat.udl.eps.etrapp.android.controllers.UserController;
import timber.log.Timber;

public class EtrappInstanceService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Timber.d("Refreshed token: %s", refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        if (UserController.getInstance().isUserLoggedIn()) {
            UserController.getInstance().updateNotificationToken();
        }
    }

}
