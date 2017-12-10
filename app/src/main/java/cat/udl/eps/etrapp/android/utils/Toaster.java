package cat.udl.eps.etrapp.android.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class Toaster {

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, @StringRes int message) {
        show(context, context.getString(message));
    }

}
