package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;


public class RegisterActivity extends BaseActivity {


    @BindView(R.id.new_user_firstname) EditText firstname;
    @BindView(R.id.new_user_lastname) EditText lastname;
    @BindView(R.id.new_user_email) EditText email;
    @BindView(R.id.new_user_username) EditText username;
    @BindView(R.id.new_user_password) EditText password;

    @BindView(R.id.new_user_button) Button button;

    public static Intent start(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @OnClick(R.id.new_user_button) void eventClickRegister() {

        UserController.getInstance().createUser(firstname.getText().toString(),
                                                lastname.getText().toString()).
        addOnSuccessListener(response -> {
            System.out.println("deu bom.");
            System.out.println(response);
        }).addOnFailureListener(response ->{
            System.out.println("deu ruim");
        });


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
