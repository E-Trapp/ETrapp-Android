package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;

import butterknife.BindView;
import butterknife.OnClick;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.RC_SIGN_UP;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.sign_in_button) Button button;
    @BindView(R.id.sign_in_username) EditText username;
    @BindView(R.id.sign_in_password) EditText password;

    public static Intent start(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @OnClick(R.id.sign_up_button) void startSignUpFlow() {
        startActivityForResult(RegisterActivity.start(this), RC_SIGN_UP);
    }

    @Override protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override protected void configView() {
        button.setOnClickListener(v -> {
            getApp().showDialog(this, "Logging in...");
            UserController.getInstance()
                    .authenticate(username.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(s -> {
                        getApp().dismissDialog();
                        setResult(RESULT_OK);
                        finish();
                    })
                    .addOnFailureListener(t -> {
                        Timber.d(t);
                        getApp().dismissDialog();
                    })
            ;
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
