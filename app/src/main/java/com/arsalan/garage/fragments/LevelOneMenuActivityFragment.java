package com.arsalan.garage.fragments;


import android.app.Activity;
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
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelOneMenuActivityFragment extends Fragment {


    private static final String TAG = "LevelOneMenuActivityFragment";
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String mScreenType;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private int mNumberOfColumns;
    private Bundle mBundle;
    private String scrapType;

    public LevelOneMenuActivityFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        mScreenType = mBundle.getString(AppConstants.SCREEN_TYPE);
        scrapType = mBundle.getString(AppConstants.SCRAP_TYPE);
        if (mScreenType.equals( AppConstants.SCREEN_SCRAP)) {
            if (scrapType.equals(AppConstants.SCRAP_MAIN)) {
                mNumberOfColumns = 2;
            } else {
                mNumberOfColumns = AppConstants.NUM_OF_COLUMNS;
            }

        } else {
            mNumberOfColumns = AppConstants.NUM_OF_COLUMNS;
        }
        initMenuItems();
    }

    private void initMenuItems() {
        if (mScreenType == AppConstants.SCREEN_ROAD_HELP) {
            mMenuItemsArrayList = Utils.getRoadHelpScreenMenuItems(getActivity());
        } else if (mScreenType == AppConstants.SCREEN_SCRAP) {
            if (scrapType == AppConstants.SCRAP_MAIN) {
                mMenuItemsArrayList = Utils.getScrapScreenMenuItems();
            }
        } else if (mScreenType == AppConstants.SCREEN_KARAJAT) {
            mMenuItemsArrayList = Utils.getKarajatScreenMenuItems();
        } else if (mScreenType == AppConstants.SCREEN_TYPE_TAXI) {
            mMenuItemsArrayList = Utils.getTaxiMenuItems();
        } else if (mScreenType == AppConstants.SCREEN_FOR_RENT) {
            mMenuItemsArrayList = Utils.getTaxiMenuItems();
        } else if (mScreenType == AppConstants.SCREEN_KHIDMAT_SHAMLA) {
            mMenuItemsArrayList = Utils.getKhidmatShamlaMenuItems();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new GridLayoutManager(getActivity(), mNumberOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        recyclerViewAdapter = new RecyclerViewAdapter(mMenuItemsArrayList);

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        //RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        //RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(recyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mMenuItemsArrayList != null && !mMenuItemsArrayList.isEmpty()) {

                    mOnMenuItemClickListener.onMenuItemClick(mMenuItemsArrayList.get(position), position);

                      if(scrapType == AppConstants.SCRAP_AMERICA && position == 0){
                          Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
//                    Logger.i("UserId", mMenuItemsArrayList.get(position).getMenuTitle());
                         getActivity().startActivity(intent);

                      }
//                    Intent intent = new Intent(getActivity(), LevelTwoMenuActivity.class);
//                    Logger.i("UserId", mMenuItemsArrayList.get(position).getMenuTitle());
//                    getActivity().startActivity(intent);
                }
            }
        }));


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnMenuItemClickListener) {
            mOnMenuItemClickListener = (OnMenuItemClickListener) activity;
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(HomeMenuItem homeMenuItem, int position);
    }

}



