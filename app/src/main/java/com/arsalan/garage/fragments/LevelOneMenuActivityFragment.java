package com.arsalan.garage.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
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


    public LevelOneMenuActivityFragment() {
        // Required empty public constructor
    }


    private static final String TAG = "LevelOneMenuActivityFragment";
    /*Number of columns in the grid view*/
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int mScreenType;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private int mNumberOfColumns;
    private Bundle mBundle;
    private  int scrapType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        mScreenType = mBundle.getInt(AppConstants.SCREEN_TYPE);
        scrapType = mBundle.getInt(AppConstants.SCRAP_TYPE);
        if(mScreenType == AppConstants.SCREEN_SCRAP){
            if(scrapType == AppConstants.SCRAP_MAIN){
                mNumberOfColumns = 2;
            }else{
                mNumberOfColumns = AppConstants.NUM_OF_COLUMNS;
            }

        }else {
            mNumberOfColumns = AppConstants.NUM_OF_COLUMNS;
        }
        initMenuItems();
    }

    private void initMenuItems() {
        if(mScreenType == AppConstants.SCREEN_ROAD_HELP){
            mMenuItemsArrayList = Utils.getRoadHelpScreenMenuItems(getActivity());
        }else if(mScreenType == AppConstants.SCREEN_SCRAP){
            if(scrapType == AppConstants.SCRAP_MAIN){
                mMenuItemsArrayList = Utils.getScrapScreenMenuItems();
            }else if(scrapType == AppConstants.SCRAP_AMERICA){
                mMenuItemsArrayList = Utils.getScrapAmericaMenuItems();
            }else if(scrapType == AppConstants.SCRAP_AWARBY){
                mMenuItemsArrayList = Utils.getScrapAwarbiMenuItems();
            }else if(scrapType == AppConstants.SCRAP_ASIBI){
                mMenuItemsArrayList = Utils.getScrapAsibiMenuItems();
            }else if(scrapType == AppConstants.SCRAP_TAUSIL_KATA){
                mMenuItemsArrayList = Utils.getScrapTawsilKataMenuItems();
            }
        }else if(mScreenType == AppConstants.SCREEN_KARAJAT){
            mMenuItemsArrayList = Utils.getKarajatScreenMenuItems();
        }else if(mScreenType == AppConstants.SCREEN_TAXI){
            mMenuItemsArrayList = Utils.getTaxiMenuItems();
        }else if(mScreenType == AppConstants.SCREEN_FOR_RENT){
            mMenuItemsArrayList = Utils.getTaxiMenuItems();
        }else if(mScreenType == AppConstants.SCREEN_KHIDMAT_SHAMLA){
            mMenuItemsArrayList = Utils.getKhidmatShamlaMenuItems();
        }

    }

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


        layoutManager = new GridLayoutManager(getActivity(),mNumberOfColumns);

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

//                    Intent intent = new Intent(getActivity(), LevelTwoMenuActivity.class);
//                    Logger.i("UserId", mMenuItemsArrayList.get(position).getMenuTitle());
//                    getActivity().startActivity(intent);
                }
            }
        }));


        return rootView;
    }



//    /** Creates and returns the data items to be shown in the Recycler View*/
//    private ArrayList<DataModel> getDataModelList(){
//        ArrayList<DataModel> dataModels = new ArrayList<>();
//
//        for (int i = 0; i < mMenuItemsArrayList.size(); i++) {
//            dataModels.add(new DataModel("Title:"+i));
//        }
//
//        return dataModels;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof  OnMenuItemClickListener){
            mOnMenuItemClickListener = (OnMenuItemClickListener) activity;
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(HomeMenuItem homeMenuItem, int position);
    }

    }


    /** Adds a single item in the existing list*/
//    private final void addItem(){
//        //Adding item at top second position(i.e. at index 1).
//        //mDataModels.set(1, new DataModel("Added Item "+String.valueOf(++addItemCount)));
//        //See for similar methods to know more item insertion options
//        recyclerViewAdapter.notifyItemInserted(1);
//    }

    /** Deletes a single item from the existing list*/
//    private void deleteItem(){
//        //Removing top item
//        //mDataModels.remove(0);
//        //See for similar methods to know more item removing options
//        recyclerViewAdapter.notifyItemRemoved(0);
//    }


//    private ArrayList<HomeMenuItem> getMenuItems(){
//
//        mMenuItemsArrayList = new ArrayList<>();
//
//        mMenuItemsArrayList.add(new HomeMenuItem(R.drawable.subgrag_icon1, "الشويخ"));
//        mMenuItemsArrayList.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الري"));
//        mMenuItemsArrayList.add(new HomeMenuItem(R.drawable.subgrag_icon1, "شرق"));
//        mMenuItemsArrayList.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الجهراء"));
//        mMenuItemsArrayList.add(new HomeMenuItem(R.drawable.subgrag_icon1, "صليبيه"));
//        mMenuItemsArrayList.add(new HomeMenuItem(R.drawable.subgrag_icon2, "الفحيحيل"));
//        return mMenuItemsArrayList;
//    }



