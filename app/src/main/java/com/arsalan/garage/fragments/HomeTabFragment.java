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

import com.arsalan.garage.models.DataModel;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.ForRentActivity;
import com.arsalan.garage.activities.KhidmatShamlaActivity;
import com.arsalan.garage.activities.LevelOneMenuActivity;
import com.arsalan.garage.activities.RoadHelpActivity;
import com.arsalan.garage.activities.ScrapMainMenuActivity;
import com.arsalan.garage.activities.TaxiActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment {


    public HomeTabFragment() {
        // Required empty public constructor
    }


    private static final String TAG = "PlaceholderFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    private static final int NUM_OF_ITEMS = 12;
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int addItemCount = 0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview);


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
                if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
                    HomeMenuItem homeMenuItem = mHomeMenuItemArrayList.get(position);
                    Intent intent = null;
                    if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_KARAJAT)){
                         intent = new Intent(getActivity(), LevelOneMenuActivity.class);
                    }else if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_HELP_ON_ROAD)){
                        intent = new Intent(getActivity(), RoadHelpActivity.class);
                    }else if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_SCRAP)){
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_SCRAP);
                        bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_MAIN);
                        intent = new Intent(getActivity(), ScrapMainMenuActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);
                    }else if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_TAXI)){
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TAXI);
                        intent = new Intent(getActivity(), TaxiActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);

                    }else if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_FOR_RENT)){
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_FOR_RENT);
                        intent = new Intent(getActivity(), ForRentActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);

                    }else if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_KHIDMAT_SHAMLA)){
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_KHIDMAT_SHAMLA);
                        intent = new Intent(getActivity(), KhidmatShamlaActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);

                    }else {
                        return;
                        //intent = new Intent(getActivity(), LevelOneMenuActivity.class);
                    }

                    Logger.i("UserId", mHomeMenuItemArrayList.get(position).getMenuTitle());
                    getActivity().startActivity(intent);
                }
            }
        }));

        return rootView;
    }



    /** Creates and returns the data items to be shown in the Recycler View*/
    private ArrayList<DataModel> getDataModelList(){
        ArrayList<DataModel> dataModels = new ArrayList<>();

        for (int i = 0; i < NUM_OF_ITEMS; i++) {
            dataModels.add(new DataModel("Title:"+i));
        }

        return dataModels;
    }


    /** Adds a single item in the existing list*/
    private final void addItem(){
        //Adding item at top second position(i.e. at index 1).
        //mDataModels.set(1, new DataModel("Added Item "+String.valueOf(++addItemCount)));
        //See for similar methods to know more item insertion options
        recyclerViewAdapter.notifyItemInserted(1);
    }

    /** Deletes a single item from the existing list*/
    private void deleteItem(){
        //Removing top item
        //mDataModels.remove(0);
        //See for similar methods to know more item removing options
        recyclerViewAdapter.notifyItemRemoved(0);
    }


    private ArrayList<HomeMenuItem> getMenuItems(){

        mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gragesjpg, "كراجات", AppConstants.MENU_ITEM_TYPE_KARAJAT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.hellpthecarhome, " المساعده على الطريق", AppConstants.MENU_ITEM_TYPE_HELP_ON_ROAD));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.scrap, "السكراب", AppConstants.MENU_ITEM_TYPE_SCRAP));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.taxicopy, "تكسي",AppConstants.MENU_ITEM_TYPE_TAXI));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.rent_car, "تأجير سيارات", AppConstants.MENU_ITEM_TYPE_FOR_RENT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.checkingcar, "فحص فني" ));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.carwash, "غسيل متنقل"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.tinting8, "حمايه وتظليل"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.manautoservice, " خدمات شامله" , AppConstants.MENU_ITEM_TYPE_KHIDMAT_SHAMLA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.sspeedtrail, "السرعه والاداء العالي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.boats, "مارين"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.autoservice, "الوكالات"));
        return mHomeMenuItemArrayList;
    }


}
