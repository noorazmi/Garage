package com.arsalan.garage.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arsalan.garage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturesFragment extends Fragment {

    private LinearLayout mLinearLayoutContainer;

    public FeaturesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_features, container, false);
        mLinearLayoutContainer = (LinearLayout) rootView.findViewById(R.id.scroll_view_container);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateView();
    }

    private void populateView(){
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_car_des_item, null);
        mLinearLayoutContainer.addView(view);


    }
}
