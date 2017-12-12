package cat.udl.eps.etrapp.android.application;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


public class ETrappApplication extends MultiDexApplication {

    private ProgressDialog progressDialog;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLibs();
    }

    private void initializeLibs() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
        Timber.plant(new Timber.DebugTree());
        Fresco.initialize(this);
    }

    public void showDialog(Context context) {
        showDialog(context, "");
    }

    public void showDialog(Context context, String text) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
