package com.arsalan.garage.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.AlwakalatServiceCentersActivity;
import com.arsalan.garage.activities.AlwakalatSparePartsActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlwakalatCarServiceFragment extends Fragment {


    private static final String TAG = "AlwakalatCarServiceFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 2;
    /*Total number of items in the RecyclerView*/
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int addItemCount = 0;


    public AlwakalatCarServiceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_alwakalat_car, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        RecyclerView.LayoutManager layoutManager = null;

        layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        recyclerViewAdapter = new RecyclerViewAdapter(getMenuItems());

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        /// RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(recyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomeMenuItem homeMenuItem = null;
                if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
                    homeMenuItem = mHomeMenuItemArrayList.get(position);
                }

                Bundle bundle = new Bundle();
                Intent intent = null;

                switch (position) {
                    case 0:
                        //bundle.putString(AppConstants.URL, Urls.ALWAKALAT_AGENCIES);
                        bundle.putString(AppConstants.EXTRA_TITLE, homeMenuItem.getMenuTitle());
                        bundle.putString(AppConstants.EXTRA_URL, Urls.SERVICE_CENTERS_BASE_URL+getArguments().getString(AppConstants.EXTRA_URL));
                        bundle.putString(AppConstants.EXTRA_SERVICE_TYPE, AppConstants.EXTRA_SERVICE_TYPE_CENTERS);
                        //bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ARABIC);
                        //Intent intent = new Intent(getActivity(), CategorySaleStaticListActivity.class);
                        intent = new Intent(getActivity(), AlwakalatServiceCentersActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                        break;
                    case 1:
                        //bundle.putString(AppConstants.URL, Urls.ALWAKALAT_AGENCIES);
                        bundle.putString(AppConstants.EXTRA_TITLE, homeMenuItem.getMenuTitle());
                        bundle.putString(AppConstants.EXTRA_URL, Urls.SPAREPARTS_BASE_URL+getArguments().getString(AppConstants.EXTRA_URL));
                        bundle.putString(AppConstants.EXTRA_SERVICE_TYPE, AppConstants.EXTRA_SERVICE_TYPE_SPARE_PARTS);
                        //bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ARABIC);
                        //Intent intent = new Intent(getActivity(), CategorySaleStaticListActivity.class);
                        intent = new Intent(getActivity(), AlwakalatSparePartsActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);

                        break;
                    default:
                        break;
                }
            }
        }));

        return rootView;


    }


    private ArrayList<HomeMenuItem> getMenuItems() {

        mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.carservice, "مراكز الخدمة"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage9, "قطع الغيار"));
        return mHomeMenuItemArrayList;
    }

}





        /*extends Fragment {


    public AlwakalatCarServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alwakalat_car, container, false);
    }

}*/
