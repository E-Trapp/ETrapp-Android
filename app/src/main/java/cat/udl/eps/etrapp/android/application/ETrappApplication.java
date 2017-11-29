package cat.udl.eps.etrapp.android.application;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;


public class ETrappApplication extends MultiDexApplication {

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
}
