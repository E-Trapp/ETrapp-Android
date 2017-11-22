package cat.udl.eps.etrapp.android.application;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

import timber.log.Timber;


public class ETrappApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeLibs();
    }

    private void initializeLibs() {
        Timber.plant(new Timber.DebugTree());
        Fresco.initialize(this);
    }
}
