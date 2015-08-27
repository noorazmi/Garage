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

import com.arsalan.garage.DataModel;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.CategorySaleListActivity;
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
public class LevelTwoMenuActivityFragment extends Fragment {


    public LevelTwoMenuActivityFragment() {
        // Required empty public constructor
    }


    private static final String TAG = "LevelTwoMenuActivityFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    private static final int NUM_OF_ITEMS = 6;
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int addItemCount = 0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview);


        RecyclerView.LayoutManager layoutManager = null;
//        String type = getArguments().getString(MainActivity.TYPE);
//        if( type.equals(MainActivity.TYPE_VERTICAL_LIST)){
//            /*LinearLayoutManager to show a vertical list view*/
//            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        }else if(type.equals(MainActivity.TYPE_HORIZONTAL_LIST)){
//            /*LinearLayoutManager to show a horizontal list view*/
//            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        }else if(type.equals(MainActivity.TYPE_GRID_VIEW)){
//             /*LinearLayoutManager to show a grid view. We can specify number of columns in the grid.*/
//            layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMNS);
//
//        }else if(type.equals(MainActivity.TYPE_HORIZONTAL_GRID_VIEW_STAGGERED)){
//             /*LinearLayoutManager to show a staggered grid view. We can specify number of columns in the grid.*/
//            //spanCount:   If orientation is vertical, spanCount is number of columns. If orientation is horizontal, spanCount is number of rows.
//            //orientation: StaggeredGridLayoutManager.HORIZONTAL or StaggeredGridLayoutManager.HORIZONTAL
//            layoutManager = new StaggeredGridLayoutManager(3/*span count*/, StaggeredGridLayoutManager.HORIZONTAL/* orientation*/);
//
//        }else if(type.equals(MainActivity.TYPE_VERTICAL_GRID_VIEW_STAGGERED)){
//             /*LinearLayoutManager to show a staggered grid view. We can specify number of columns in the grid.*/
//            //spanCount:   If orientation is vertical, spanCount is number of columns. If orientation is horizontal, spanCount is number of rows.
//            //orientation: StaggeredGridLayoutManager.HORIZONTAL or StaggeredGridLayoutManager.HORIZONTAL
//            layoutManager = new StaggeredGridLayoutManager(2/*span count*/, StaggeredGridLayoutManager.VERTICAL/* orientation*/);
//
//        }

        layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        recyclerViewAdapter = new RecyclerViewAdapter(getMenuItems());

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        //RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
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
                    Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
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

        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage1, "ميكانيكي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage2, "جيرات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage3, "صبغ وحداده"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage4, "ميزان وهيئه"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage5, "تنجيد"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage6, "اطارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage7, "زجاج"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage8, "كهرباء وتكييف"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage9, "قطع غيار"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage10, "برمجة سيارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage11, "تعديل رنجات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage12, "بريكات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage13, "مخرطه"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage14, "عوادم وراديترات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage15, "طلي معادن"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage16, "زينه سيارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage17, "مفاتيح"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage18, "مغاسل سيارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage19, "تلميع سيارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage20, "هايدروليك"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage21, "شفط صدمات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.player, "مسجلات"));
        return mHomeMenuItemArrayList;
    }


}
