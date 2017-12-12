package cat.udl.eps.etrapp.android.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.application.ETrappApplication;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        configView();
    }

    public ETrappApplication getApp() {
        return (ETrappApplication) getApplicationContext();
    }

    protected abstract int getLayout();

    protected abstract void configView();

    protected void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    protected void loadFragment(Fragment fragment, String tag) {
        loadFragment(fragment, tag, true);
    }

    protected Fragment findFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    protected void loadFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment, tag);
        if (addToBackStack) ft.addToBackStack(tag);
        ft.commit();
    }

    protected void scrollFragment(String tag) {
        Timber.d("Perform scroll");
        ScrollableFragment scrollableFragment = (ScrollableFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (scrollableFragment != null && scrollableFragment.isVisible())
            scrollableFragment.scroll();
    }

    protected ActionBar getCurrentActionBar() {
        if (getSupportActionBar() != null) {
            return getSupportActionBar();
        }
        return null;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
