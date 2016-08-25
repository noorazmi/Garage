package com.arsalan.garage.fragments;

import android.content.Intent;
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
import com.arsalan.garage.activities.CarWashDetailsActivity;
import com.arsalan.garage.activities.CarWashListActivity;
import com.arsalan.garage.adapters.CarWashListAdapter1;
import com.arsalan.garage.adapters.CustomRecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.CarWashVO;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 06/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CarWashListFragment extends Fragment {


    private static final String TAG = "CarWashListFragment";
    private static final int NUM_OF_COLUMNS = 2;
    private RecyclerView mRecyclerView;
    private CarWashListAdapter1 recyclerViewAdapter;
    private CarWashVO mCarWashVO;
    protected int pageNumber = 0;
    protected boolean isFirstTime = true;
    protected int mTotalItemCount;
    protected boolean keepLoading = true;
    protected ArrayList<CarWashVO.CarWash> mCarWashListItems;


    public CarWashListFragment() {
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
                Intent intent = new Intent(getActivity(), CarWashDetailsActivity.class);
                Bundle bundle = new Bundle();
                CarWashVO.CarWash carWash = mCarWashVO.getResults().get(position);
                bundle.putString(AppConstants.ID, carWash.getCar_wash_id());
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
        httpRequest.setUrl(Urls.MOVABLE_WASH + "?page=all");
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(CarWashVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(getActivity(), httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mCarWashVO = (CarWashVO) httpResponse.getValueObject();
                if(mCarWashVO == null || mCarWashVO.getResults() == null){
                    Utils.showSnackBar(getView().findViewById(R.id.root_layout), getString(R.string.error_something_went_wrong));
                    return;
                }
                showData(mCarWashVO.getResults());
                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }

    private void showData(ArrayList<CarWashVO.CarWash> carWashListItems){
        recyclerViewAdapter.setDownloadingProgress(false, carWashListItems);
        mCarWashListItems.addAll(carWashListItems);
        if (mCarWashListItems.size() >= mTotalItemCount) {
            keepLoading = false;
        }
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setLoaded();
    }

    private void setAdapter() {
        recyclerViewAdapter = new CarWashListAdapter1(getActivity(), mRecyclerView, mCarWashListItems);
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
        httpRequest.setUrl(Urls.MOVABLE_WASH + "?page=all");
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(CarWashVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(getActivity(), httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                if(httpResponse.getValueObject() == null || ((CarWashVO) httpResponse.getValueObject()).getResults() == null){
                    Utils.showSnackBar(getView().findViewById(R.id.root_layout), getString(R.string.error_something_went_wrong));
                    return;
                }
                mTotalItemCount = ((CarWashVO) httpResponse.getValueObject()).getData_count();
                ((CarWashListActivity) getActivity()).setNoOfItemsInTooBar(mTotalItemCount);
                if(mTotalItemCount >= 0){
                    performGET();
                }
            }
        });
        loaderHandler.loadData();
    }
}
