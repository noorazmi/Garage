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
import android.widget.Toast;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.AlwakalatAgencySubMenu1Activity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlwakalatAgencyMenuFragment extends Fragment {


    private static final String TAG = "AlwakalatAgencyMenuFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    //private static final int NUM_OF_ITEMS = 12;
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int addItemCount = 0;


    public AlwakalatAgencyMenuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_alwakalat_agency_menu, container, false);
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
                    Intent intent = new Intent(getActivity(), AlwakalatAgencySubMenu1Activity.class);
                    intent.putExtra(AppConstants.EXTRA_TITLE, homeMenuItem.getMenuTitle());
                    intent.putExtra(AppConstants.EXTRA_URL, homeMenuItem.getUrl());
                    if(homeMenuItem.getUrl() == null){
                        Toast.makeText(getActivity(), "Not Available", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    getActivity().startActivity(intent);
                }
            }
        }));

        return rootView;


    }


    private ArrayList<HomeMenuItem> getMenuItems() {

        mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.cadillac1, "كاديلاك", null, Urls.SHOWROOM_CADILLAC));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.acura, "اكورا", null, Urls.SHOWROOM_ACURA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.volvo, "فولفو", null, Urls.SHOWROOM_VOLVO));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.baic, "بيك", null, Urls.SHOWROOM_BAIC));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.changan, "شنقن", null, Urls.SHOWROOM_CHANGAN));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.chrysler, "كرايسلر", null,  Urls.SHOWROOM_CHRYSLER));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.citroen, "سيترن", null, Urls.SHOWROOM_CITROEN));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.dfm, "دفم", null, Urls.SHOWROOM_DFM));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.dodge, "دودج", null, Urls.SHOWROOM_DODGE));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.fiat, "فيت", null, Urls.SHOWROOM_FIAT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gac_motor, "جك متر", null, Urls.SHOWROOM_GAC));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mg, "م ق" , Urls.SHOWROOM_MG));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.ram, "رم", null, Urls.SHOWROOM_RAM));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.jeep7, "جيب", null,  Urls.SHOWROOM_JEEP));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.volkswagen4, "فولكس", null, Urls.SHOWROOM_VOLKSWAGEN));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.peugeot, "بوقت", null, Urls.SHOWROOM_PEUGEOT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.toyota, "تويوتا", null, Urls.SHOWROOM_TOYOTA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.geely, "قيلي" , null, Urls.SHOWROOM_GEELY));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mazda, "مازدا" , null, Urls.SHOWROOM_MAZDA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.honda, "هوندا" , null, Urls.SHOWROOM_HONDA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.renault, "رنولت" , null, Urls.SHOWROOM_RENAULT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mitsubishi, "ميتسوبيشي", null,Urls.SHOWROOM_MITSUBISHI));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.infinit, "انفينتي", null, Urls.SHOWROOM_INFINITI));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.chevrolet3, "شيفروليه", null, Urls.SHOWROOM_CHEVOLATE));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.ford, "فورد"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.renault, "رينو"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.nissan, "نيسان"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.suzuki, "سوزوكي"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.lincoln, "لنكلن"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.srt_hemi, "سرت إمي"));
        return mHomeMenuItemArrayList;
    }

    /*private ArrayList<HomeMenuItem> getMenuItems() {

        mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.cadillac1, "كاديلاك", null, Urls.HOUSE_DISPLAY_CADILLAC));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.acura, "اكورا", null, Urls.HOUSE_DISPLAY_ACURA));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.volvo, "فولفو", null, Urls.HOUSE_DISPLAY_VOLVO));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.chrysler, "كرايسلر", null,  Urls.HOUSE_DISPLAY_CHRYSLER));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.dodge, "دودج", null, Urls.HOUSE_DISPLAY_DODGE));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.chevrolet3, "شيفروليه"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gmc4, "جمس"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.ford, "فورد"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.hummerlogo6, "همر"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.jeep7, "جيب"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mercedesbenz1, "مرسيدس"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.bmw2, "بي ام دبليو"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.land_rover3, "رنج روفر"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.volkswagen4, "فولكس"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.jaguarlogo5, "جاغوار"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.porsche6, "بورش"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.oodcar7, "اودي"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.peugeot8, "بيجو / ستروين"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.skoda9, "سكودا"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mini10, "ميني"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.car11, "رينو"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.toyota, "تويوتا"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.nissan, "نيسان"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.lexus_name, "لكزس"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.infinit, "انفينتي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.honda, "هوندا"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.hyundai, "هيونداي"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.kia_logo, "كيا"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mitsubishi, "ميتسوبيشي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.suzuki, "سوزوكي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mazda, "مازدا"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.isuzu, "ايسوزو"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.sbaru12, "سوبارو"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.cherylogo, "شيري"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.tata, "تاتا"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.great, "جريت ويل"));
        //mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.ssangyong, "سانج يونج"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.changan, "شنقن", null, Urls.HOUSE_DISPLAY_CHANGAN));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.citroen, "سيترن", null, Urls.HOUSE_DISPLAY_CITROEN));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.lincoln, "لنكلن"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.peugeot, "بوقت"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.ram, "رم", null, Urls.HOUSE_DISPLAY_RAM));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.baic, "بيك", null, Urls.HOUSE_DISPLAY_BAIC));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.dfm, "دفم", null, Urls.HOUSE_DISPLAY_DFM));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.fiat, "فيت", null, Urls.HOUSE_DISPLAY_FIAT));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gac_motor, "جك متر", null, Urls.HOUSE_DISPLAY_GAC));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.srt_hemi, "سرت إمي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mg, "م ق" , Urls.HOUSE_DISPLAY_MG));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.geely, "قيلي" ));
        return mHomeMenuItemArrayList;
    }*/
}



