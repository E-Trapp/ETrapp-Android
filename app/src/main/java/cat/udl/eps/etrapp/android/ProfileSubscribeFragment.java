package cat.udl.eps.etrapp.android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.udl.eps.etrapp.android.models.User;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;


public class ProfileSubscribeFragment extends BaseFragment {


    private static User user;

    public static ProfileSubscribeFragment newInstance(User u) {
        ProfileSubscribeFragment fragment = new ProfileSubscribeFragment();
        user = u;
        return fragment;
    }

    @Override protected int getLayout() {
        return R.layout.fragment_profile_subscribe;
    }

    @Override protected void configView(View fragmentView) {

    }


}
