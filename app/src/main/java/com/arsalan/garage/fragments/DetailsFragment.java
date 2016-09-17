package com.arsalan.garage.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    public DetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ((TextView)rootView.findViewById(R.id.textview_year)).setText(getArguments().getString(AppConstants.EXTRA_YEAR));
        ((TextView)rootView.findViewById(R.id.textview_engine)).setText(getArguments().getString(AppConstants.EXTRA_ENGINE));
        ((TextView)rootView.findViewById(R.id.textview_transmission)).setText(getArguments().getString(AppConstants.EXTRA_TRANSMISSION));
        ((TextView)rootView.findViewById(R.id.textview_wheel_drive)).setText(getArguments().getString(AppConstants.EXTRA_PAYMENT));
        ((TextView)rootView.findViewById(R.id.textview_price)).setText(getArguments().getString(AppConstants.EXTRA_PRICE));
        return rootView;
    }


}
