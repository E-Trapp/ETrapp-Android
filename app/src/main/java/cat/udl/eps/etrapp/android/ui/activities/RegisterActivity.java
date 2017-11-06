package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;


public class RegisterActivity extends BaseActivity {

    public static Intent start(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override protected int getLayout() {
        return R.layout.activity_sign_up;
    }

    @Override protected void configView() {
        getCurrentActionBar().setTitle(getString(R.string.sign_up));
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
