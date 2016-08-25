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
import com.arsalan.garage.activities.MarineBoatFishingActivity;
import com.arsalan.garage.activities.MarineBoatFishingDetailsActivity;
import com.arsalan.garage.adapters.CustomRecyclerViewAdapter;
import com.arsalan.garage.adapters.MarineBoatFishingListAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.BoatFishingListVO;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 07/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineBoatFishingListFragment extends Fragment {


    private static final String TAG = "MarineBoatFishingFrag";
    private static final int NUM_OF_COLUMNS = 2;
    private RecyclerView mRecyclerView;
    private MarineBoatFishingListAdapter recyclerViewAdapter;
    private BoatFishingListVO mBoatFishingListVO;
    protected int pageNumber = 0;
    protected boolean isFirstTime = true;
    protected int mTotalItemCount;
    protected boolean keepLoading = true;
    protected ArrayList<BoatFishingListVO.BoatFishing> mCarWashListItems;


    public MarineBoatFishingListFragment() {
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
        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        /// RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), MarineBoatFishingDetailsActivity.class);
                Bundle bundle = new Bundle();
                BoatFishingListVO.BoatFishing boatFishing = mBoatFishingListVO.getResults().get(position);
                bundle.putString(AppConstants.ID, boatFishing.getBoat_fishing_id());
                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        }));
        mCarWashListItems = new ArrayList<>(0);
        setAdapter();
        if (Utils.isNetworkAvailable()) {
            setTotalCount();
        } else {
            Utils.showSnackBar(rootView.findViewById(R.id.root_layout), getString(R.string.no_network_connection));
        }

        return rootView;


    }


    private void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        if(isFirstTime){
            httpRequest.setShowProgressDialog(true);
            isFirstTime = false;
        }
        //Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + getArguments().getString(AppConstants.EXTRA_URL));
        //httpRequest.setUrl(Urls.MOVABLE_WASH + "?page="+(++pageNumber)+"&limit="+AppConstants.REQUEST_ITEM_COUNT);
        httpRequest.setUrl(Urls.MARINE_BOAT_FISHING_LIST + "?page=all");
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(BoatFishingListVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(getActivity(), httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mBoatFishingListVO = (BoatFishingListVO) httpResponse.getValueObject();
                if(mBoatFishingListVO == null || mBoatFishingListVO.getResults() == null){
                    Utils.showSnackBar(getView().findViewById(R.id.root_layout), getString(R.string.error_something_went_wrong));
                    return;
                }
                showData(mBoatFishingListVO.getResults());
                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }

    private void showData(ArrayList<BoatFishingListVO.BoatFishing> carWashListItems){
        recyclerViewAdapter.setDownloadingProgress(false, carWashListItems);
        mCarWashListItems.addAll(carWashListItems);
        if (mCarWashListItems.size() >= mTotalItemCount) {
            keepLoading = false;
        }
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setLoaded();
    }

    private void setAdapter() {
        recyclerViewAdapter = new MarineBoatFishingListAdapter(getActivity(), mRecyclerView, mCarWashListItems);
        mRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnPullUpListener(new CustomRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (keepLoading) {
                    recyclerViewAdapter.setDownloadingProgress(true, mCarWashListItems);
                    performGET();
                }
            }
        });
    }

    private void setTotalCount(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        httpRequest.setUrl(Urls.MARINE_BOAT_FISHING_LIST + "?page=all");
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(BoatFishingListVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(getActivity(), httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                if(httpResponse.getValueObject() == null || ((BoatFishingListVO) httpResponse.getValueObject()).getResults() == null){
                    Utils.showSnackBar(getView().findViewById(R.id.root_layout), getString(R.string.error_something_went_wrong));
                    return;
                }
                mTotalItemCount = ((BoatFishingListVO) httpResponse.getValueObject()).getData_count();
                ((MarineBoatFishingActivity) getActivity()).setNoOfItemsInTooBar(mTotalItemCount);
                if(mTotalItemCount >= 0){
                    performGET();
                }
            }
        });
        loaderHandler.loadData();
    }
}
