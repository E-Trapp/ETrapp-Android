package cat.udl.eps.etrapp.android.ui.fragments;

import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import cat.udl.eps.etrapp.android.R;
import cat.udl.eps.etrapp.android.models.Event;
import cat.udl.eps.etrapp.android.ui.base.BaseFragment;

public class FeaturedEventFragment extends BaseFragment {

    public static FeaturedEventFragment newInstance(Event event) {
        Bundle args = new Bundle();
        FeaturedEventFragment fragment = new FeaturedEventFragment();
        fragment.setArguments(args);
        fragment.event = event;
        return fragment;
    }

    @BindView(R.id.featured_image) SimpleDraweeView featuredImage;

    private Event event;

    @Override protected int getLayout() {
        return R.layout.fragment_featured_event;
    }

    @Override protected void configView(View fragmentView) {
        featuredImage.setImageURI(event.getImageUrl());
    }
}
