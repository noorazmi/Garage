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
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        ((TextView) rootView.findViewById(R.id.textview_phone_number)).setText(getArguments().getString(AppConstants.EXTRA_CONTACT));
        return rootView;
    }


}
