package com.arsalan.garage.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;

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
        final View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
        final TextView textviewPhone1 = ((TextView) rootView.findViewById(R.id.textview_phone1));
        final TextView textviewPhone2 = ((TextView) rootView.findViewById(R.id.textview_phone2));
        ((TextView) rootView.findViewById(R.id.textview_phone_number)).setText(getArguments().getString(AppConstants.EXTRA_CONTACT_INFO));
        String[] phoneNumber = getArguments().getStringArray(AppConstants.EXTRA_CONTACT);
        if(phoneNumber != null && phoneNumber.length >= 1){
            textviewPhone1.setText(phoneNumber[0]);
            if(phoneNumber.length >= 2){
                textviewPhone2.setText(phoneNumber[1]);
            }
        }

        textviewPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.initCall(textviewPhone1.getText().toString(), getActivity());
            }
        });
        textviewPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.initCall(textviewPhone2.getText().toString(), getActivity());
            }
        });

        return rootView;
    }


}
