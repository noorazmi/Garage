package com.arsalan.garage.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.AlwakalatAgencyDescriptionActivity;
import com.arsalan.garage.adapters.AlwakalatAgencySubMenu2Adapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.HouseDisplayVo;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;


public class AlwakalatAgencySubMenu2Fragment extends Fragment {


    private static final String TAG = "PlaceholderFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 2;
    /*Total number of items in the RecyclerView*/
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private AlwakalatAgencySubMenu2Adapter recyclerViewAdapter;
    private int addItemCount = 0;
    private HouseDisplayVo mHouseDisplayVo;


    public AlwakalatAgencySubMenu2Fragment() {
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

        //recyclerViewAdapter = new RecyclerViewAdapter(getMenuItems());

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        /// RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //mRecyclerView.setAdapter(recyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomeMenuItem homeMenuItem = null;
                if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
                    homeMenuItem = mHomeMenuItemArrayList.get(position);
                }
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), AlwakalatAgencyDescriptionActivity.class);
                HouseDisplayVo.CarModel carModel = mHouseDisplayVo.getResults().get(position);
                intent.putExtra(AppConstants.EXTRA_CAR_ID, carModel.getShowroom_car_id());
                getActivity().startActivity(intent);
            }
        }));

        if(Utils.isNetworkAvailable()){
            performGET();
        }else {
            Utils.showSnackBar(rootView.findViewById(R.id.root_layout), getString(R.string.no_network_connection));
        }

        return rootView;


    }


    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.URL_ITEM_DESCRIPTION_BASE + getArguments().getString(AppConstants.ID)));
        httpRequest.setUrl(getArguments().getString(AppConstants.EXTRA_URL));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(HouseDisplayVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mHouseDisplayVo = (HouseDisplayVo) httpResponse.getValueObject();
                setAdapter();

                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }

    private void setAdapter(){
        if(mHouseDisplayVo == null){
            return;
        }
        ArrayList<HouseDisplayVo.CarModel> carModelArrayList = mHouseDisplayVo.getResults();
        recyclerViewAdapter = new AlwakalatAgencySubMenu2Adapter(carModelArrayList);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }
}




