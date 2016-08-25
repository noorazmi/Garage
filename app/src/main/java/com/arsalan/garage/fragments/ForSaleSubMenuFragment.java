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
import com.arsalan.garage.activities.ForSaleListActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForSaleSubMenuFragment extends Fragment {

    private String scrapeType;
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private int mNumberOfColumns = 3;
    private RecyclerViewAdapter recyclerViewAdapter;

    public ForSaleSubMenuFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrapeType = getActivity().getIntent().getExtras().getString(AppConstants.SCRAP_TYPE);
        initMenuItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_buy_and_sale_sub_menu, container, false);
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
                    String makeRegion = null;
                    String make = null;
                    if (scrapeType.equals(AppConstants.SCRAP_AMERICA)) {
                        makeRegion = AppConstants.AMERICAN;
                        if (position == 0) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_CADILLAC);
                            make = AppConstants.CADILLAC;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_cadillac));
                        } else if (position == 1) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_DODGENCHRYSLER);
                            //bundle.putString(AppConstants.URL, Urls.DODGENCHRYSLER);
                            make = AppConstants.DODGE_CHRYSLER;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.dodge));
                        } else if (position == 2) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_CHEVROLET);
                            //bundle.putString(AppConstants.URL, Urls.CHEVROLET);
                            make = AppConstants.CHEVROLET;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_chevrolet));
                        } else if (position == 3) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_GMC);
                            //bundle.putString(AppConstants.URL, Urls.GMC);
                            make = AppConstants.GMC;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_gmc));
                        } else if (position == 4) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_MERCURY);
                            //bundle.putString(AppConstants.URL, Urls.GMC);
                            make = AppConstants.MERCURY;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_mercury));
                        } else if (position == 5) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_RAM);
                            //bundle.putString(AppConstants.URL, Urls.GMC);
                            make = AppConstants.RAM;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_ram));
                        }else if (position == 6) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_FORDNLINCOLN);
                            //bundle.putString(AppConstants.URL, Urls.FORDNLINCOLN);
                            make = AppConstants.FORD;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.ford));
                        } else if (position == 7) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_LINCOLN);
                            //bundle.putString(AppConstants.URL, Urls.FORDNLINCOLN);
                            make = AppConstants.LINCOLN;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.lincoln));
                        } else if (position == 8) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_HUMMER);
                            //bundle.putString(AppConstants.URL, Urls.HUMMER);
                            make = AppConstants.HUMMER;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_hummer));
                        } else if (position == 9) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_JEEP);
                            //bundle.putString(AppConstants.URL, Urls.JEEP);
                            make = AppConstants.JEEP;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_jeep));
                        }else if (position == 10) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_JEEP);
                            //bundle.putString(AppConstants.URL, Urls.JEEP);
                            make = AppConstants.CHRYSLER;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.chrysler));
                        }else if (position == 11) {
                            bundle.putString(AppConstants.SCRAP_AMERICA_SUB_TYPE, AppConstants.SCRAP_AMERICA_SUB_TYPE_JEEP);
                            //bundle.putString(AppConstants.URL, Urls.JEEP);
                            make = AppConstants.OTHER;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_other));
                        }
                    } else if (scrapeType.equals(AppConstants.SCRAP_EUROPEAN)) {
                        makeRegion = AppConstants.EUROPEAN;
                        if (position == 0) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_MERCEDES);
                            //bundle.putString(AppConstants.URL, Urls.MERCEDES);
                            make = AppConstants.MERCEDES;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_mercedes));
                        } else if (position == 1) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_BMW);
                            //bundle.putString(AppConstants.URL, Urls.BMW);
                            make = AppConstants.BMW;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_bmw));
                        } else if (position == 2) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_RANGE_ROVER);
                            bundle.putString(AppConstants.URL, Urls.RANGEROVER);
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_rangerover));
                        } else if (position == 3) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_VOLKSWAGEN);
                            //bundle.putString(AppConstants.URL, Urls.VOLVOWAGON);
                            make = AppConstants.VOLVES_WAGAN;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_volvowagon));
                        } else if (position == 4) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_JAGUAR);
                            //bundle.putString(AppConstants.URL, Urls.JAGUAR);
                            make = AppConstants.JAGUAR;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_jaguar));
                        } else if (position == 5) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_PORCHE);
                            //bundle.putString(AppConstants.URL, Urls.PORSCHE);
                            make = AppConstants.PORCHE;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_porsche));
                        } else if (position == 6) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_AUDI);
                            //bundle.putString(AppConstants.URL, Urls.AUDI);
                            make = AppConstants.AUDI;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_audi));
                        } else if (position == 7) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_PEUGEOT_CITROEN);
                            //bundle.putString(AppConstants.URL, Urls.PEUGEOT);
                            make = AppConstants.PEUGEOT_CITROEN;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_peugeot));
                        } else if (position == 8) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_SKODA);
                            //bundle.putString(AppConstants.URL, Urls.SKODA);
                            make = AppConstants.SKODA;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_skoda));
                        } else if (position == 9) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_MINI);
                            //bundle.putString(AppConstants.URL, Urls.MINI);
                            make = AppConstants.MINI;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_mini));
                        } else if (position == 10) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_RENAULT);
                            //bundle.putString(AppConstants.URL, Urls.RENAULT);
                            make = AppConstants.RENAULT;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_renault));
                        }else if (position == 11) {
                            bundle.putString(AppConstants.SCRAP_AWARBY_SUB_TYPE, AppConstants.SCRAP_AWARBY_SUB_TYPE_RENAULT);
                            //bundle.putString(AppConstants.URL, Urls.RENAULT);
                            make = AppConstants.OTHER;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_other));
                        }
                    } else if (scrapeType.equals(AppConstants.SCRAP_ASIAN)) {
                        makeRegion = AppConstants.ASIAN;
                        if (position == 0) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_TOYOTA);
                            //bundle.putString(AppConstants.URL, Urls.TOYOTA);
                            make = AppConstants.TOYOTA;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_toyota));
                        } else if (position == 1) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_NISSAN);
                            //bundle.putString(AppConstants.URL, Urls.NISSAN);
                            make = AppConstants.NISSAN;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_nissan));
                        } else if (position == 2) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_LEXUS);
                            //bundle.putString(AppConstants.URL, Urls.LEXUS);
                            make = AppConstants.LEXUS;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_lexus));
                        } else if (position == 3) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_INFINITI);
                            //bundle.putString(AppConstants.URL, Urls.INFINITI);
                            make = AppConstants.INFINITI;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_infiniti));
                        } else if (position == 4) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_HONDA);
                            //bundle.putString(AppConstants.URL, Urls.HONDA);
                            make = AppConstants.HONDA;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_honda));
                        } else if (position == 5) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_HYUNDAI);
                            //bundle.putString(AppConstants.URL, Urls.HYUNDAI);
                            make = AppConstants.HYUNDAI;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_hyundai));
                        } else if (position == 6) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_KIA);
                            //bundle.putString(AppConstants.URL, Urls.KIA);
                            make = AppConstants.KIA;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_kia));
                        } else if (position == 7) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_MITSUBISHI);
                            //bundle.putString(AppConstants.URL, Urls.MITSUBISHI);
                            make = AppConstants.MITSUBISHI;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_mitsubishi));
                        } else if (position == 8) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SUZUKI);
                            //bundle.putString(AppConstants.URL, Urls.SUZUKI);
                            make = AppConstants.SUZUKI;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_suzuki));
                        } else if (position == 9) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_MAZDA);
                            //bundle.putString(AppConstants.URL, Urls.MAZDA);
                            make = AppConstants.MAZDA;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_mazda));
                        } else if (position == 10) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_ISUZA);
                            //bundle.putString(AppConstants.URL, Urls.ISUZA);
                            make = AppConstants.ISUZU;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_isuza));
                        } else if (position == 11) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SUBARU);
                            //bundle.putString(AppConstants.URL, Urls.SUBARU);
                            make = AppConstants.SUBARU;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_subaru));
                        }else if (position == 12) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SUBARU);
                            //bundle.putString(AppConstants.URL, Urls.SUBARU);
                            make = AppConstants.OTHER;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_other));
                        } else if (position == 13) {
                            bundle.putString(AppConstants.SCRAP_ASIA_SUB_TYPE, AppConstants.SCRAP_ASIA_SUB_TYPE_SHERI);
                            //bundle.putString(AppConstants.URL, Urls.MERCEDES);
                            make = AppConstants.MERCEDES;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_sheri));
                        } else if (scrapeType.equals(AppConstants.SCRAP_DELIVERY)) {
                            bundle.putString(AppConstants.SCRAP_DELIVERY_SUB_TYPE, AppConstants.SCRAP_DELIVERY_SUB_TYPE_NONE);
                            //bundle.putString(AppConstants.URL, Urls.DELIVERY);
                            //make = AppConstants.;
                            bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.car_brand_sheri));
                        } else {
                            return;
                        }
                    }

                    Intent intent = new Intent(getActivity(), ForSaleListActivity.class);
                    bundle.putString(AppConstants.URL, Urls.FORSALE_BASE + makeRegion + "/" + make + "/" + PrefUtility.getAccessToken());
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            }
        }));


        return rootView;
    }

    private void initMenuItems() {

        if (scrapeType.equals(AppConstants.SCRAP_AMERICA)) {
            mMenuItemsArrayList = Utils.geBuyAndSaleAmericaMenuItems();
        } else if (scrapeType.equals(AppConstants.SCRAP_EUROPEAN)) {
            mMenuItemsArrayList = Utils.getBuyAndSaleEuropeanMenuItems();
        } else if (scrapeType.equals(AppConstants.SCRAP_ASIAN)) {
            mMenuItemsArrayList = Utils.getBuyAndSaleAsianMenuItems();
        } else if (scrapeType.equals(AppConstants.SCRAP_DELIVERY)) {
            mMenuItemsArrayList = Utils.getScrapTawsilKataMenuItems();
        }
    }
}