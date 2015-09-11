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
import com.arsalan.garage.activities.ScrapSubMenuActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;

public class ScrapMainMenuFragment extends Fragment {



    private static final String TAG = "ScrapMainMenuFragment";
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int mNumberOfColumns;
    private Bundle mBundle;
    private String scrapType;


    public ScrapMainMenuFragment() {  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        scrapType = mBundle.getString(AppConstants.SCRAP_TYPE);
        mNumberOfColumns = 2;
        initMenuItems();
    }

    private void initMenuItems() {
        mMenuItemsArrayList = Utils.getScrapScreenMenuItems();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_scrap_main_menu, container, false);
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
                    Bundle bundle = new Bundle();
                    if (position == 0) {
                        bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_AMERICA);
                    }else if(position == 1){
                        bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_EUROPEAN);
                    }else if(position == 2){
                        bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_ASIAN);
                    }else if(position == 3){
                        bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_TAUSIL_KATA);
                    }
//                        Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
                    Intent intent = new Intent(getActivity(), ScrapSubMenuActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);

                }
            }
        }));


        return rootView;
    }
}
