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
public class OfferFragment extends Fragment {


    public OfferFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offer, container, false);
    }


}
