package com.arsalan.garage.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.ScrapUserDetailsActivity;
import com.arsalan.garage.activities.ScrapUserListActivity;
import com.arsalan.garage.adapters.CustomRecyclerViewAdapter;
import com.arsalan.garage.adapters.ScrapUserListAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.ScrapUserListData;

import java.util.ArrayList;
import java.util.List;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ScrapUserListFragment extends android.app.Fragment {

    private static final String TAG = "ScrapUserListFragment";
    private RecyclerView mRecyclerView;
    private ScrapUserListAdapter mCategoryListAdapter;
    private ScrapUserListData mMarineUserData;
    private List<ScrapUserListData.ScrapUserListItem> mAccessoriesUserItems;
    private int pageNumber = 0;
    private boolean isFirstTime = true;
    private boolean keepLoading = true;
    private int mTotalItemCount;


    public ScrapUserListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marine_user_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        return rootView;
    }

    private void initVariables(){
        mCategoryListAdapter = null;
        mMarineUserData = null;
        mAccessoriesUserItems = null;
        pageNumber = 0;
        isFirstTime = true;
        keepLoading = true;
        mTotalItemCount = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isNetworkAvailable(getActivity())) {
            initVariables();
            performGET();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        setAdapter();
    }

    private void setAdapter() {

        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        //mCategoryListAdapter = new CategoryListAdapter(americanCarsVO, getActivity().getIntent().getStringExtra(AppConstants.SCRAP_TYPE), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
        mAccessoriesUserItems = new ArrayList<>(0);
        mCategoryListAdapter = new ScrapUserListAdapter(getActivity(), mRecyclerView, mAccessoriesUserItems, getActivity().getIntent().getStringExtra(AppConstants.SCRAP_TYPE), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));


        RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCategoryListAdapter);
        mCategoryListAdapter.setOnPullUpListener(new CustomRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (keepLoading) {
                    mCategoryListAdapter.setDownloadingProgress(true, mAccessoriesUserItems);
                    performGET();
                }
            }
        });

        mCategoryListAdapter.addOnClickCallback(new CustomRecyclerViewAdapter.OnClickCallback() {
            @Override
            public void onViewClickListener(View v, int position) {
                Toast.makeText(GarageApp.getInstance(), "hello " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(getActivity(), ScrapUserDetailsActivity.class);
                Bundle bundle = new Bundle();
                ScrapUserListData.ScrapUserListItem result = mMarineUserData.getResults().get(position);
                bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
                bundle.putString(AppConstants.IMAGE_URL, result.getImage());
                bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
                bundle.putString(AppConstants.ID, result.getScrap_id());
                bundle.putString(AppConstants.URL, getArguments().getString(AppConstants.URL));
                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
            }
        }));

    }

    private void showData(List<ScrapUserListData.ScrapUserListItem> accessoriesUserItems) {
        mCategoryListAdapter.setDownloadingProgress(false, mAccessoriesUserItems);
        mAccessoriesUserItems.addAll(accessoriesUserItems);
        mCategoryListAdapter.notifyDataSetChanged();
        mCategoryListAdapter.setLoaded();
    }

    private void performGET() {
        if (!Utils.isNetworkAvailable()) {
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


        HTTPRequest httpRequest = new HTTPRequest();
        if (isFirstTime) {
            httpRequest.setShowProgressDialog(true);
        }

        Bundle bundle = getArguments();
        String url = bundle.getString(AppConstants.URL) + "/" + PrefUtility.getAccessToken();
        String pageLimitUrl = url + "?page=" + (++pageNumber) + "?limit=" + AppConstants.REQUEST_ITEM_COUNT;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + pageLimitUrl);
        httpRequest.setUrl(pageLimitUrl);
        //httpRequest.setUrl(url);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ScrapUserListData.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mMarineUserData = (ScrapUserListData) httpResponse.getValueObject();
                if (mMarineUserData == null || mMarineUserData.getResults() == null) {
                    return;
                }

                showData(mMarineUserData.getResults());

                if (mMarineUserData.getData_count() != null && isFirstTime) {
                    mTotalItemCount = Integer.valueOf(mMarineUserData.getData_count());
                    setTotalCount();
                    isFirstTime = false;
                }else {
                    Utils.showSnackBar(getActivity(), mMarineUserData.getMessage());
                }

                if (mAccessoriesUserItems.size() >= mTotalItemCount) {
                    keepLoading = false;
                }
                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }

    private void setTotalCount() {
        ((ScrapUserListActivity) getActivity()).setNoOfItemsInTooBar(mTotalItemCount);
    }
}
