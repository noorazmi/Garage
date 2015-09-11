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
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;


public class ScrapSubMenuFragment extends Fragment {

    private String scrapeType;
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private int mNumberOfColumns = 3;
    private RecyclerViewAdapter recyclerViewAdapter;
    public ScrapSubMenuFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrapeType = getActivity().getIntent().getExtras().getString(AppConstants.SCRAP_TYPE);
        initMenuItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_scrap_sub_menu, container, false);
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

                    //mOnMenuItemClickListener.onMenuItemClick(mMenuItemsArrayList.get(position), position);

                    Bundle bundle = new Bundle();
                    if(scrapeType.equals(AppConstants.SCRAP_AMERICA)){

                        if(position == 0){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_CADILLAC);
                            bundle.putString(AppConstants.URL, Urls.CADILLAC);
                        }else if(position == 1){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_DODGENCHRYSLER);
                            bundle.putString(AppConstants.URL, Urls.DODGENCHRYSLER);
                        }else if(position == 2){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_CHEVROLET);
                            bundle.putString(AppConstants.URL, Urls.CHEVROLET);
                        }else if(position == 3){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_GMC);
                            bundle.putString(AppConstants.URL, Urls.GMC);
                        }else if(position == 4){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_FORDNLINCOLN);
                            bundle.putString(AppConstants.URL, Urls.FORDNLINCOLN);
                        }else if(position == 5){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_HUMMER);
                            bundle.putString(AppConstants.URL, Urls.HUMMER);
                        }else if(position == 6){
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_JEEP);
                            bundle.putString(AppConstants.URL, Urls.JEEP);
                        }
                    }else if(scrapeType.equals(AppConstants.SCRAP_EUROPEAN)){
                        if(position == 0){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_MERCEDES);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 1){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_BMW);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 2){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_RANGE_ROVER);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 3){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_VOLKSWAGEN);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 4){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_JAGUAR);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 5){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_PORCHE);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 6){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_AUDI);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 7){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_PEUGEOT_CITROEN);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 8){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_SKODA);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 9){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_MINI);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else if(position == 10){
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_RENAULT);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }
                    }else if(scrapeType.equals(AppConstants.SCRAP_ASIAN)){
                        if(position == 0){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_TOYOTA);
                            bundle.putString(AppConstants.URL, Urls.TOYOTA);
                        }else if(position == 1){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_NISSAN);
                            bundle.putString(AppConstants.URL, Urls.NISSAN);
                        }else if(position == 2){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_LEXUS);
                            bundle.putString(AppConstants.URL, Urls.LEXUS);
                        }else if(position == 3){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_INFINITI);
                            bundle.putString(AppConstants.URL, Urls.INFINITI);
                        }else if(position == 4){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_HONDA);
                            bundle.putString(AppConstants.URL, Urls.HONDA);
                        }else if(position == 5){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_HYUNDAI);
                            bundle.putString(AppConstants.URL, Urls.HYUNDAI);
                        }else if(position == 6){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_KIA);
                            bundle.putString(AppConstants.URL, Urls.KIA);
                        }else if(position == 7){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_MITSUBISHI);
                            bundle.putString(AppConstants.URL, Urls.MITSUBISHI);
                        }else if(position == 8){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SUZUKI);
                            bundle.putString(AppConstants.URL, Urls.SUZUKI);
                        }else if(position == 9){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_MAZDA);
                            bundle.putString(AppConstants.URL, Urls.MAZDA);
                        }else if(position == 10){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_ISUZA);
                            bundle.putString(AppConstants.URL, Urls.ISUZA);
                        }else if(position == 11){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SUBARU);
                            bundle.putString(AppConstants.URL, Urls.SUBARU);
                        }else if(position == 12){
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SHERI);
                            bundle.putString(AppConstants.URL, Urls.MERCEDES);
                        }else {
                            return;
                        }
                    }

                    Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            }
        }));


        return rootView;
    }

    private void initMenuItems(){

        if (scrapeType.equals(AppConstants.SCRAP_AMERICA)) {
            mMenuItemsArrayList = Utils.getScrapAmericaMenuItems();
        } else if (scrapeType.equals(AppConstants.SCRAP_EUROPEAN)) {
            mMenuItemsArrayList = Utils.getScrapAwarbiMenuItems();
        } else if (scrapeType.equals(AppConstants.SCRAP_ASIAN)) {
            mMenuItemsArrayList = Utils.getScrapAsibiMenuItems();
        } else if (scrapeType.equals(AppConstants.SCRAP_TAUSIL_KATA)) {
            mMenuItemsArrayList = Utils.getScrapTawsilKataMenuItems();
        }
    }
}
