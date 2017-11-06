package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.utils.Mockups;

public class UserProfileActivity extends BaseActivity {

    private User user;

    public static Intent start(Context context, long owner) {
        return new Intent(context, UserProfileActivity.class)
                .putExtra("userKey", owner);
    }

    @Override protected int getLayout() {
        return R.layout.activity_user_profile;
    }

    @Override protected void configView() {
        handleIntent(getIntent());
        getCurrentActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("userKey")) {
                user = Mockups.getUserById(intent.getLongExtra("userKey", -1));
                getCurrentActionBar().setTitle(user.getUsername());
            }
        }
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
