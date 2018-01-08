package cat.udl.eps.etrapp.android.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cat.udl.eps.etrapp.android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineDialogFragment extends Fragment {


    public OfflineDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offline_dialog, container, false);
    }

}
