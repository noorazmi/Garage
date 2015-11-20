package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModelFragment extends Fragment {

    /*main info

    السنه - year
    المحرك - engine
    ناقل الحركه - transmission
    نظام الدفع - drive system
    السعر - price

    2- المزايا -  features
            (Up to six points)
    .
            .
            .
            .
            .
            .( for more info please call) لمعرفة المزيد يرجى الإتصال

    3- اتصل بنا - contact

    معلومات عن البائع والمعرض - info about seller and showroom

*/


    public ModelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_model, container, false);
        ((TextView)rootView.findViewById(R.id.textview_description1)).setText(getArguments().getString(AppConstants.EXTRA_ENGINE));
        ((TextView)rootView.findViewById(R.id.textview_description2)).setText(getArguments().getString(AppConstants.EXTRA_TRANSMISSION));
        ((TextView)rootView.findViewById(R.id.textview_description3)).setText(getArguments().getString(AppConstants.EXTRA_PAYMENT));
        ((TextView)rootView.findViewById(R.id.textview_description3)).setText(getArguments().getString(AppConstants.EXTRA_PRICE));
        ((TextView)rootView.findViewById(R.id.textview_description3)).setText(getArguments().getString(AppConstants.EXTRA_DESCRIPTION));

        return rootView;
    }


}
