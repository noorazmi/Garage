package com.arsalan.garage.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
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
import com.arsalan.garage.adapters.UserListAdapter;
import com.arsalan.garage.adapters.CustomRecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.UserBaseData;
import com.arsalan.garage.vo.UserListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;
import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 16/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public abstract class UserListBaseFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected UserListAdapter mCategoryListAdapter;
    protected int pageNumber = 0;
    protected boolean isFirstTime = true;
    protected boolean keepLoading = true;
    protected ArrayList<UserListItem> mUserListItems;
    protected int mTotalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marine_user_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        return rootView;
    }

    private void initVariables(){
        mCategoryListAdapter = null;
        mUserListItems = null;
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
        mUserListItems = new ArrayList<>(0);
        mCategoryListAdapter = new UserListAdapter(getActivity(), mRecyclerView, mUserListItems, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));


        RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCategoryListAdapter);
        mCategoryListAdapter.setOnPullUpListener(new CustomRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (keepLoading) {
                    mCategoryListAdapter.setDownloadingProgress(true, mUserListItems);
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

                Intent intent = new Intent(getActivity(), getDetailsActivityClass());
                Bundle bundle = new Bundle();
                UserListItem userListItem = mUserListItems.get(position);
                bundle.putString(AppConstants.DESCRIPTION, userListItem.getDescription());
                bundle.putString(AppConstants.IMAGE_URL, userListItem.getImage());
                bundle.putString(AppConstants.PHONE_NUMBER, userListItem.getPhone());
                bundle.putString(AppConstants.ID, userListItem.getId());
                bundle.putString(AppConstants.URL, getArguments().getString(AppConstants.URL));
                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
            }
        }));

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

        //Bundle bundle = getArguments();
        //String url = bundle.getString(AppConstants.URL) + "/" + PrefUtility.getAccessToken();
        String url = getListDownloadUrl();
        Log.e("TAG", " ******^^^^^^^^^bundle URL:" + url);
        httpRequest.setUrl(url + "?page=" + (++pageNumber) + "&limit=" + AppConstants.REQUEST_ITEM_COUNT);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(getValueObjectFullyQualifiedName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                JSONObject jsonObjectData = null;
                ValueObject valueObject = httpResponse.getValueObject();
                try {
                    jsonObjectData = new JSONObject(((HTTPResponse) httpModel).getResponseJSONString());
                    ((UserBaseData) valueObject).setStatus(jsonObjectData.optString(AppConstants.STATUS));
                    ((UserBaseData) valueObject).setMessage(jsonObjectData.optString(AppConstants.MESSAGE));
                    ((UserBaseData) valueObject).setData_count(jsonObjectData.optString(AppConstants.DATA_COUNT));
                    setListData(valueObject, jsonObjectData.getJSONArray(AppConstants.RESULTS));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        loaderHandler.loadData();
    }

    protected void showData(ArrayList<UserListItem> userListItems) {
        mCategoryListAdapter.setDownloadingProgress(false, mUserListItems);
        mUserListItems.addAll(userListItems);
        mCategoryListAdapter.notifyDataSetChanged();
        mCategoryListAdapter.setLoaded();

        if (mUserListItems.size() >= mTotalItemCount) {
            keepLoading = false;
        }
    }

    protected void setUserItemValues(JSONObject jsonObject, UserListItem userListItem) {
        userListItem.setDescription(jsonObject.optString(AppConstants.DESCRIPTION));
        userListItem.setMake(jsonObject.optString(AppConstants.MAKE));
        userListItem.setMake_region_name(jsonObject.optString(AppConstants.MAKE_REGION_NAME));
        userListItem.setPhone(jsonObject.optString(AppConstants.PHONE));
        userListItem.setPost_date(jsonObject.optString(AppConstants.POST_DATE));
        userListItem.setImage(jsonObject.optString(AppConstants.IMAGE));
        userListItem.setPrice(jsonObject.optString(AppConstants.PRICE));
        userListItem.setTitle(jsonObject.optString(AppConstants.TITLE));
        userListItem.setIs_owner(jsonObject.optInt(AppConstants.IS_OWNER));
    }


    abstract protected String getListDownloadUrl();

    abstract protected void setListData(ValueObject valueObject, JSONArray responseJSONString);

    abstract protected String getValueObjectFullyQualifiedName();
    abstract protected Class getDetailsActivityClass();



}
