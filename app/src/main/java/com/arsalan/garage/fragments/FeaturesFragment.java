package com.arsalan.garage.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeaturesFragment extends Fragment {

    private LinearLayout mLinearLayoutContainer;
    private String description;
    private String[] descriptinItems;


    public FeaturesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_features, container, false);
        mLinearLayoutContainer = (LinearLayout) rootView.findViewById(R.id.scroll_view_container);
        description = getArguments().getString(AppConstants.EXTRA_DESCRIPTION);
        descriptinItems = description.split("\r\n");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateView();
    }

    private void populateView(){
        for (int i = 0; i < descriptinItems.length ; i++) {
            if(!TextUtils.isEmpty(descriptinItems[i])){
                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = layoutInflater.inflate(R.layout.layout_car_des_item, null);
                ((TextView) view.findViewById(R.id.textview_description)).setText(descriptinItems[i]);
                mLinearLayoutContainer.addView(view);
            }
        }
    }
}
