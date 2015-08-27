package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.arsalan.garage.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfferTabFragment extends Fragment {

    private static final String TAG = "LeaderBoardFragment";

    public OfferTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_offer_tab, container, false);


        return rootView;
    }



}
