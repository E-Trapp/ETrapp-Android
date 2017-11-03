package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    public static Intent start(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @BindView(R.id.sign_in_button) Button button;

    @Override protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override protected void configView() {
        button.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}
