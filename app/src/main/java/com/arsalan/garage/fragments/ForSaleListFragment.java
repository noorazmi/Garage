package com.arsalan.garage.fragments;


import android.app.Fragment;
import android.os.Bundle;

import com.arsalan.garage.activities.ForSaleDetailsActivity;
import com.arsalan.garage.activities.ForSaleListActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.ForSaleUserListData;
import com.arsalan.garage.vo.UserListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForSaleListFragment extends UserListBaseFragment {

    private static final String TAG = "ForSaleListFragment";
    private ForSaleUserListData mForSaleUserListData;

    public ForSaleListFragment() {
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_buy_and_sale_list, container, false);
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//        if (Utils.isNetworkAvailable(getActivity())) {
//            performGET();
//        } else {
//            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
//        }
//
//        return rootView;
//    }

//    private void setAdapter(ForSaleUserListData forSaleUserListData) {
//        if (forSaleUserListData == null) {
//            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//            alertDialog.setTitle("Error!");
//            alertDialog.setMessage("There is network or server problem. Please try again.");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            getActivity().finish();
//                        }
//                    });
//            alertDialog.show();
//            return;
//        }
//        ((ForSaleListActivity) getActivity()).setNoOfItemsInTooBar(forSaleUserListData.getResults().size());
//        RecyclerView.LayoutManager layoutManager = null;
//        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//
//        mRecyclerView.setLayoutManager(layoutManager);
//        //mDataModels = getDataModelList();
//
//        mBuyAndSaleListAdapter = new BuyAndSaleListAdapter(forSaleUserListData, getActivity(), getActivity().getIntent().getStringExtra(AppConstants.SCRAP_TYPE), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
//        RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(1);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(mBuyAndSaleListAdapter);
//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                if (AppConstants.SCRAP_DELIVERY.equals(getArguments().getString(AppConstants.SCRAP_TYPE))) {
//                    return;
//                }
//                Intent intent = new Intent(getActivity(), ForSaleDetailsActivity.class);
//                Bundle bundle = new Bundle();
//                ForSaleUserListData.ForSaleItem result = mForSaleUserListData.getResults().get(position);
//                bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
//                bundle.putString(AppConstants.IMAGE_URL, result.getImage());
//                bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
//                bundle.putString(AppConstants.EXTRA_FORSALE_ID, result.getForsale_id());
//                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
//                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
//            }
//        }));
//
//    }

//    private void performGET() {
//        HTTPRequest httpRequest = new HTTPRequest();
//        httpRequest.setShowProgressDialog(true);
//        Bundle bundle = getArguments();
//        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + bundle.getString(AppConstants.URL));
//        httpRequest.setUrl(getArguments().getString(AppConstants.URL) + "?page=all");
//        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
//        httpRequest.setValueObjectFullyQualifiedName(ForSaleUserListData.class.getName());
//        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
//        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(HTTPModel httpModel) {
//                HTTPResponse httpResponse = (HTTPResponse) httpModel;
//                mForSaleUserListData = (ForSaleUserListData) httpResponse.getValueObject();
//                setAdapter(mForSaleUserListData);
//                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
//            }
//        });
//        loaderHandler.loadData();
//    }

    @Override
    protected String getListDownloadUrl() {
        Bundle bundle = getArguments();
        String url = bundle.getString(AppConstants.URL);
        return url;
    }

    @Override
    protected void setListData(ValueObject valueObject, JSONArray resultsJsonArray) {
            mForSaleUserListData = (ForSaleUserListData) valueObject;

            try {
                int size = resultsJsonArray.length();
                ArrayList<UserListItem> mForSaleItems = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = resultsJsonArray.getJSONObject(i);
                    ForSaleUserListData.ForSaleItem forSaleItem = new ForSaleUserListData.ForSaleItem();
                    forSaleItem.setForsale_id(jsonObject.optString(AppConstants.EXTRA_FORSALE_ID));
                    setUserItemValues(jsonObject, forSaleItem);
                    mForSaleItems.add(forSaleItem);
                }

                mForSaleUserListData.setResults(mForSaleItems);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mForSaleUserListData == null || mForSaleUserListData.getResults() == null) {
                return;
            }
            showData(mForSaleUserListData.getResults());
            if (isFirstTime) {
                setTotalCount(mForSaleUserListData.getData_count());
                isFirstTime = false;
            }
        }

    private void setTotalCount(String totalCount) {
        try {
            ((ForSaleListActivity) getActivity()).setNoOfItemsInTooBar(Integer.parseInt(totalCount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return ForSaleUserListData.class.getName();
    }

    @Override
    protected Class getDetailsActivityClass() {
        return ForSaleDetailsActivity.class;
    }


}
