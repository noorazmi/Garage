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
import com.arsalan.garage.activities.AlwakalatAgencyMenuActivity;
import com.arsalan.garage.activities.CategorySaleListActivity;
import com.arsalan.garage.activities.BuyAndSaleMenuActivity;
import com.arsalan.garage.activities.HelpOnRoadActivity;
import com.arsalan.garage.activities.KarajatMenuActivity;
import com.arsalan.garage.activities.KhidmatShamlaActivity;
import com.arsalan.garage.activities.ScrapMainMenuActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;

import java.util.ArrayList;


public class HomeTabFragment extends Fragment {

    public HomeTabFragment() {}


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
                if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
                    HomeMenuItem homeMenuItem = mHomeMenuItemArrayList.get(position);
                    Intent intent = null;
                    String menuType = homeMenuItem.getMenuType();
                    if(menuType == null){
                        return;
                    }
                    if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_KARAJAT)) {
                        intent = new Intent(getActivity(), KarajatMenuActivity.class);
                    } else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_HELP_ON_ROAD)) {
                        intent = new Intent(getActivity(), HelpOnRoadActivity.class);
                    } else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_SCRAP)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_SCRAP);
                        bundle.putString(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_MAIN);
                        intent = new Intent(getActivity(), ScrapMainMenuActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);
                    } else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_TAXI)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_TAXI);
                        bundle.putString(AppConstants.URL, Urls.TAXI);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.taxi));
                        intent = new Intent(getActivity(), CategorySaleListActivity.class);
                        intent.putExtras(bundle);

                    }else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_AGENCIES)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_AGENCIES);
                        //bundle.putString(AppConstants.URL, Urls.AGENCIES);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.agencies));
                        intent = new Intent(getActivity(), AlwakalatAgencyMenuActivity.class);
                        //intent.putExtras(bundle);

                    }else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_MOVABLE_WASH)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_MOVABLE_WASH);
                        bundle.putString(AppConstants.URL, Urls.MOVABLE_WASH);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.movable_wash));
                        bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH);
                        intent = new Intent(getActivity(), CategorySaleListActivity.class);
                        intent.putExtras(bundle);

                    }else if(homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_TINTINGCAR)){
                        Bundle bundle = new Bundle();
                        //bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_MOVABLE_WASH);
                        bundle.putString(AppConstants.URL, Urls.TINTINGCAR);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.tintingcar));
                        bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH);
                        intent = new Intent(getActivity(), CategorySaleListActivity.class);
                        intent.putExtras(bundle);

                    }else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_TECHNICAL_INSPECTION)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_TECHNICAL_INSPECTION);
                        bundle.putString(AppConstants.URL, Urls.TECHNICAL_INSPECTION);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.technical_inspection));
                        //bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH);
                        intent = new Intent(getActivity(), CategorySaleListActivity.class);
                        intent.putExtras(bundle);

                    } else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_FOR_RENT)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_FOR_RENT);
                        intent = new Intent(getActivity(), BuyAndSaleMenuActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);

                    } else if (homeMenuItem.getMenuType().equals(AppConstants.MENU_ITEM_TYPE_KHIDMAT_SHAMLA)) {
                        Bundle bundle = new Bundle();
                        //bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_KHIDMAT_SHAMLA);
                        intent = new Intent(getActivity(), KhidmatShamlaActivity.class);
                        intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);

                    } else {
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



    private ArrayList<HomeMenuItem> getMenuItems(){

        mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gragesjpg, "كراجات", AppConstants.MENU_ITEM_TYPE_KARAJAT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.hellpthecarhome, " المساعده على الطريق", AppConstants.MENU_ITEM_TYPE_HELP_ON_ROAD));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.scrap, "السكراب", AppConstants.MENU_ITEM_TYPE_SCRAP));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.taxicopy, "تكسي",AppConstants.MENU_ITEM_TYPE_TAXI));

        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.cardealer, "الوكالات",AppConstants.MENU_ITEM_TYPE_AGENCIES));


        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.carsale, "تأجير سيارات", AppConstants.MENU_ITEM_TYPE_FOR_RENT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.carsale, "بيع و شرا", AppConstants.MENU_ITEM_TYPE_FOR_RENT));

        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.carwash, "غسيل متنقل", AppConstants.MENU_ITEM_TYPE_MOVABLE_WASH));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.tinting8, "حمايه وتظليل", AppConstants.MENU_ITEM_TYPE_TINTINGCAR));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.manautoservice, " خدمات شامله" , AppConstants.MENU_ITEM_TYPE_KHIDMAT_SHAMLA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.sspeedtrail, "السرعه والاداء العالي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.boats, "مارين"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.checkingcar, "فحص فني", AppConstants.MENU_ITEM_TYPE_TECHNICAL_INSPECTION));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.autoservice, "الوكالات"));
        return mHomeMenuItemArrayList;
    }

}
