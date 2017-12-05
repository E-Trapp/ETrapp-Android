package cat.udl.eps.etrapp.android.ui.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.controllers.UserController;
import cat.udl.eps.etrapp.android.ui.activities.settings.SettingsActivity;
import cat.udl.eps.etrapp.android.ui.base.BaseActivity;
import cat.udl.eps.etrapp.android.ui.fragments.HomeFragment;
import cat.udl.eps.etrapp.android.ui.fragments.ProfileFragment;
import cat.udl.eps.etrapp.android.ui.fragments.SearchFragment;
import cat.udl.eps.etrapp.android.utils.Toaster;
import timber.log.Timber;

import static cat.udl.eps.etrapp.android.utils.Constants.RC_SIGN_IN;
import static cat.udl.eps.etrapp.android.utils.Constants.TAG_HOME_FRAGMENT;
import static cat.udl.eps.etrapp.android.utils.Constants.TAG_PROFILE_FRAGMENT;
import static cat.udl.eps.etrapp.android.utils.Constants.TAG_SEARCH_FRAGMENT;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation) BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void configView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment f;
            String tag;
            boolean showTitle = true;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tag = TAG_HOME_FRAGMENT;
                    if ((f = findFragmentByTag(tag)) == null) f = HomeFragment.newInstance();
                    break;
                case R.id.navigation_profile:
                    if (UserController.getInstance().isUserLoggedIn()) {
                        tag = TAG_PROFILE_FRAGMENT;
                        if ((f = findFragmentByTag(tag)) == null) f = ProfileFragment.newInstance();
                    } else {
                        startActivityForResult(LoginActivity.start(this), RC_SIGN_IN);
                        return false;
                    }
                    break;
                case R.id.navigation_search:
                    showTitle = false;
                    tag = TAG_SEARCH_FRAGMENT;
                    if ((f = findFragmentByTag(tag)) == null) f = SearchFragment.newInstance();
                    break;
                default:
                    return false;
            }
            loadFragment(f, tag);
            getCurrentActionBar().setDisplayShowTitleEnabled(showTitle);
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            String tag = "";
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tag = TAG_HOME_FRAGMENT;
                    break;
                case R.id.navigation_profile:
                    tag = TAG_PROFILE_FRAGMENT;
                    break;
                case R.id.navigation_search:
                    tag = TAG_SEARCH_FRAGMENT;
                    break;
            }
            scrollFragment(tag);
        });
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    Toaster.show(this, getString(R.string.success_sign_in));
                    bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
