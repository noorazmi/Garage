package com.arsalan.garage.fragments;

import android.os.Bundle;

import com.arsalan.garage.activities.AccessoriesUserDetailsActivity;
import com.arsalan.garage.activities.AccessoriesUserListActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.vo.AccessoriesUserListData;
import com.arsalan.garage.vo.UserListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesUserListFragment extends UserListBaseFragment {

    private static final String TAG = "AccessoriesUserListFrag";
    private AccessoriesUserListData mAccessoriesUserListData;
    private ArrayList<UserListItem> mAccessoriesUserItems;



    public AccessoriesUserListFragment() {
    }

    @Override
    protected String getListDownloadUrl() {
        Bundle bundle = getArguments();
        String url = bundle.getString(AppConstants.URL) + "/"+ PrefUtility.getAccessToken();
        return url;
    }

    @Override
    protected void setListData(ValueObject valueObject, JSONArray resultsJsonArray) {
        mAccessoriesUserListData = (AccessoriesUserListData) valueObject;

        try {
            int size = resultsJsonArray.length();
            mAccessoriesUserItems = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = resultsJsonArray.getJSONObject(i);
                AccessoriesUserListData.AccessoriesUserItem accessoriesUserItem = new AccessoriesUserListData.AccessoriesUserItem();
                accessoriesUserItem.setAccessories_id(jsonObject.optString(AppConstants.ACCESSORIES_ID));
                setUserItemValues(jsonObject, accessoriesUserItem);
                mAccessoriesUserItems.add(accessoriesUserItem);
            }

            mAccessoriesUserListData.setResults(mAccessoriesUserItems);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mAccessoriesUserListData == null || mAccessoriesUserListData.getResults() == null) {
            return;
        }
        showData(mAccessoriesUserListData.getResults());
        if (isFirstTime) {
            setTotalCount(mAccessoriesUserListData.getData_count());
            isFirstTime = false;
        }
    }

    private void setTotalCount(String totalCount) {
        try {
            ((AccessoriesUserListActivity) getActivity()).setNoOfItemsInTooBar(Integer.parseInt(totalCount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return AccessoriesUserListData.class.getName();
    }

    @Override
    protected Class getDetailsActivityClass() {
        return AccessoriesUserDetailsActivity.class;
    }


}
