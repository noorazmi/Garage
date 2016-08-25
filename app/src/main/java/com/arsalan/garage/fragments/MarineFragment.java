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
import com.arsalan.garage.activities.CategorySaleListActivity;
import com.arsalan.garage.activities.MarineBoatFishingActivity;
import com.arsalan.garage.activities.MarineUserActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;

import java.util.ArrayList;

public class MarineFragment extends Fragment {


    private static final String TAG = "MarineFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 2;
    /*Total number of items in the RecyclerView*/
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mMarineItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;


    public MarineFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_marine, container, false);
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
                if (mMarineItemArrayList != null && !mMarineItemArrayList.isEmpty()) {
                    homeMenuItem = mMarineItemArrayList.get(position);
                }

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_MARINE);
                Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);

                switch (position) {
                    case 0:
                        bundle.putString(AppConstants.URL, Urls.MARINE_FIBERGLASS_AND_SMITHY);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.ship_yard));
                        break;
                    case 1:
                        bundle.putString(AppConstants.URL, Urls.MARINE_MOBILE_MECHANICS_ELECTRICITY);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.repair_man));
                        break;
                    case 2:
                        bundle.putString(AppConstants.URL, Urls.MARINE_FREELY_CENTERS);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.ship_military));
                        break;
                    case 3:
                        bundle.putString(AppConstants.URL, Urls.MARINE_BOAT_FISHING_LIST);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.fishing_ship));
                        intent = new Intent(getActivity(), MarineBoatFishingActivity.class);
                        break;
                    case 4:
                        bundle.putString(AppConstants.URL, Urls.MARINE_FREELY_CENTERS);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.bai_ul_shara));
                        intent = new Intent(getActivity(), MarineUserActivity.class);
                        break;
                    default:
                        break;
                }

                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        }));

        return rootView;


    }


    private ArrayList<HomeMenuItem> getMenuItems() {

        mMarineItemArrayList = new ArrayList<>();
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.ship_yard, getString(R.string.ship_yard)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.repair_man, getString(R.string.repair_man)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.ship_military , getString(R.string.ship_military)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.fishermen , getString(R.string.fishing_ship)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.marine_in_hand , getString(R.string.bai_ul_shara)));
        return mMarineItemArrayList;
    }

}

