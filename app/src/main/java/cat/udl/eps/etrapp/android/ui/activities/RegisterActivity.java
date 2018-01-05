package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.models.UserAuth;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Toaster;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.EXTRA_USER_ID;


public class RegisterActivity extends BaseActivity {

    @BindView(R.id.new_user_firstname) EditText firstname;
    @BindView(R.id.new_user_lastname) EditText lastname;
    @BindView(R.id.new_user_email) EditText email;
    @BindView(R.id.new_user_username) EditText username;
    @BindView(R.id.new_user_password) EditText password;
    @BindView(R.id.new_user_password_confirm) EditText password_confirm;
    @BindView(R.id.new_user_button) Button actionButton;

    private User user;

    public static Intent start(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    public static Intent edit(Context context, long userKey) {
        Intent i = new Intent(context, RegisterActivity.class);
        i.putExtra(EXTRA_USER_ID, userKey);
        return i;
    }

    @Override protected int getLayout() {
        return R.layout.activity_sign_up;
    }

    @Override protected void configView() {
        handleIntent();
    }

    private void handleIntent() {
        if (getIntent().hasExtra(EXTRA_USER_ID)) {
            UserController.getInstance()
                    .getUserById(getIntent().getLongExtra(EXTRA_USER_ID, -1))
                    .addOnSuccessListener(user -> {
                        this.user = user;
                        setupUI(true);
                    });
        } else setupUI(false);
    }

    private void setupUI(boolean edit) {
        if (!edit) {
            getCurrentActionBar().setTitle(getString(R.string.sign_up));
            actionButton.setOnClickListener(v -> {
                getApp().showDialog(this, "Creating new user...");

                UserAuth userAuth = new UserAuth(firstname.getText().toString(),
                        lastname.getText().toString(),
                        email.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString());

                UserController.getInstance()
                        .createUser(userAuth)
                        .addOnCompleteListener(task -> {
                            getApp().dismissDialog();
                        })
                        .addOnSuccessListener(response -> {
                            finish();
                        })
                        .addOnFailureListener(response -> {
                            Toaster.show(this, "Error while registering, please try again.");
                        });
            });
        } else {
            getCurrentActionBar().setTitle(getString(R.string.edit));
            firstname.setText(user.getFirstName());
            lastname.setText(user.getLastName());
            email.setText(user.getEmail());
            email.setFocusable(false);
            username.setText(user.getUsername());
            username.setFocusable(false);
            actionButton.setText("Edit user");
            actionButton.setOnClickListener(v -> {
                Timber.d("Click on edit");
                getApp().showDialog(this, "Updating...");

                Map<String, Object> updates = new HashMap<>();

                // TODO: Handle errors
                if (!firstname.getText().toString().equals(user.getFirstName())) {
                    updates.put("firstName", firstname.getText().toString());
                }

                if (!lastname.getText().toString().equals(user.getLastName())) {
                    updates.put("lastName", lastname.getText().toString());
                }

                if (!password.getText().toString().isEmpty() && password.getText().toString().equals(password_confirm.getText().toString())) {
                    updates.put("password", password.getText().toString());
                }

                UserController.getInstance()
                        .editUser(user.getId(), updates)
                        .addOnCompleteListener(task -> getApp().dismissDialog())
                        .addOnSuccessListener(aVoid -> finish())
                        .addOnFailureListener(failure -> Toaster.show(this, "Error editing user."));
            });
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
