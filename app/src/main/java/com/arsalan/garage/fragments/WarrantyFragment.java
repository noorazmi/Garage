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
public class WarrantyFragment extends Fragment {

    private TextView mTextViewWarranty;
    public WarrantyFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_warranty, container, false);
        mTextViewWarranty = (TextView) rootView.findViewById(R.id.textview_warranty);
        mTextViewWarranty.setText(getArguments().getString(AppConstants.EXTRA_WARRANTY));

        return rootView;
    }



}
