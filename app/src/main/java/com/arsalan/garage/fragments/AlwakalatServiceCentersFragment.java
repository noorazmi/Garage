package com.arsalan.garage.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.AlwakalatServiceCentersActivity;
import com.arsalan.garage.adapters.ServiceCenterAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.SparePartsVo;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 09/04/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatServiceCentersFragment extends Fragment {


    private static final String TAG = "AlwakalatServiceCentrs";
    private static final int NUM_OF_COLUMNS = 1;
    private RecyclerView mRecyclerView;
    private ServiceCenterAdapter recyclerViewAdapter;
    private SparePartsVo mSparePartsModelVo;


    public AlwakalatServiceCentersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_alwakalat_spare_parts, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMNS);
        mRecyclerView.setLayoutManager(layoutManager);
        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        /// RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SparePartsVo.SparePart carModel = mSparePartsModelVo.getResults().get(position);
                /*if(carModel.getHasChild().equals("1")){
                    ((AlwakalatAgencySubMenu2Activity)getActivity()).setMenuHolderFragment(carModel.getModel(), getArguments().getString(AppConstants.EXTRA_URL)+"/"+carModel.getShowroom_car_id());
                    return;
                }
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), AlwakalatAgencyDescriptionActivity.class);
                intent.putExtra(AppConstants.EXTRA_CAR_ID, carModel.getShowroom_car_id());
                getActivity().startActivity(intent);*/
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
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + getArguments().getString(AppConstants.EXTRA_URL));
        httpRequest.setUrl(getArguments().getString(AppConstants.EXTRA_URL));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(SparePartsVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(getActivity(), httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mSparePartsModelVo = (SparePartsVo) httpResponse.getValueObject();
                setAdapter();

                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }

    private void setAdapter(){
        if(mSparePartsModelVo == null){
            return;
        }
        ArrayList<SparePartsVo.SparePart> sparePartsArrayList = mSparePartsModelVo.getResults();
        ((AlwakalatServiceCentersActivity)getActivity()).setNoOfItemsInTooBar(sparePartsArrayList.size());
        recyclerViewAdapter = new ServiceCenterAdapter(sparePartsArrayList, getActivity());
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }
}
