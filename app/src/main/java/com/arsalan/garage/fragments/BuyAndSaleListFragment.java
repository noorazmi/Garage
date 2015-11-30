package com.arsalan.garage.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.BuyAndSaleDescriptionActivity;
import com.arsalan.garage.activities.BuyAndSaleListActivity;
import com.arsalan.garage.adapters.BuyAndSaleListAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.BuyAndSaleListItemVo;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyAndSaleListFragment extends Fragment {

    private static final String TAG = "BuyAndSaleListFragment";
    private RecyclerView mRecyclerView;
    private BuyAndSaleListAdapter mBuyAndSaleListAdapter;
    private BuyAndSaleListItemVo mBuyAndSaleListItemVo;

    public BuyAndSaleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy_and_sale_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        if (Utils.isNetworkAvailable(getActivity())) {
            performGET();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }

        return rootView;
    }

    private void setAdapter(BuyAndSaleListItemVo buyAndSaleListItemVo) {
        if (buyAndSaleListItemVo == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("There is network or server problem. Please try again.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    });
            alertDialog.show();
            return;
        }
        ((BuyAndSaleListActivity) getActivity()).setNoOfItemsInTooBar(buyAndSaleListItemVo.getResults().size());
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        mBuyAndSaleListAdapter = new BuyAndSaleListAdapter(buyAndSaleListItemVo, getActivity(), getActivity().getIntent().getStringExtra(AppConstants.SCRAP_TYPE), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
        RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mBuyAndSaleListAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (AppConstants.SCRAP_DELIVERY.equals(getArguments().getString(AppConstants.SCRAP_TYPE))) {
                    return;
                }
                Intent intent = new Intent(getActivity(), BuyAndSaleDescriptionActivity.class);
                Bundle bundle = new Bundle();
                BuyAndSaleListItemVo.BuySaleItem result = mBuyAndSaleListItemVo.getResults().get(position);
                bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
                bundle.putString(AppConstants.IMAGE_URL, result.getImage());
                bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
                bundle.putString(AppConstants.EXTRA_FORSALE_ID, result.getForsale_id());
                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        }));

    }

    private void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + bundle.getString(AppConstants.URL));
        httpRequest.setUrl(getArguments().getString(AppConstants.URL) + "?page=all");
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(BuyAndSaleListItemVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mBuyAndSaleListItemVo = (BuyAndSaleListItemVo) httpResponse.getValueObject();
                setAdapter(mBuyAndSaleListItemVo);
                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }


}