package cat.udl.eps.etrapp.android.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import timber.log.Timber;


public class ETrappApplication extends Application {

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
