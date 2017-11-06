package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.sign_in_button) Button button;

    public static Intent start(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override protected void configView() {
        button.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
