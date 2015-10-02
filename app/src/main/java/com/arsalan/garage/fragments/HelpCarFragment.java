package com.arsalan.garage.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.models.DataModel;
import com.arsalan.garage.R;
import com.arsalan.garage.adapters.HelpCarAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HelpCarItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class HelpCarFragment extends Fragment {


    public HelpCarFragment() {
        // Required empty public constructor
    }


    private static final String TAG = "PlaceholderFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    private static final int NUM_OF_ITEMS = 6;
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HelpCarItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private HelpCarAdapter mCategoryListAdapter;
    private int addItemCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_help_car, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        mCategoryListAdapter = new HelpCarAdapter(getMenuItems());

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        //RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mCategoryListAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
//                    Intent intent = new Intent(getActivity(), CategoryDescriptionActivity.class);
//                    Logger.i("UserId", mHomeMenuItemArrayList.get(position).getMenuTitle());
//                    getActivity().startActivity(intent);
//                }
            }
        }));


        return rootView;
    }


    /**
     * Creates and returns the data items to be shown in the Recycler View
     */
    private ArrayList<DataModel> getDataModelList() {
        ArrayList<DataModel> dataModels = new ArrayList<>();

        for (int i = 0; i < NUM_OF_ITEMS; i++) {
            dataModels.add(new DataModel("Title:" + i));
        }

        return dataModels;
    }

    private ArrayList<HelpCarItem> getMenuItems() {

        mHomeMenuItemArrayList = new ArrayList<>();

        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:999888888"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:2222244455"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:345666777"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:345666777"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:456778888"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:999888888"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:2222244455"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:3456667777"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:3456667777"));
        mHomeMenuItemArrayList.add(new HelpCarItem("Mob:456778888"));
        return mHomeMenuItemArrayList;
    }


}
