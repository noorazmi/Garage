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
            try{
             mTotalItemCount = Integer.parseInt(mForSaleUserListData.getData_count());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            setTotalCount(String.valueOf(mTotalItemCount));
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
