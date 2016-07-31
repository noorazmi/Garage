package com.arsalan.garage.fragments;

import android.os.Bundle;

import com.arsalan.garage.activities.ScrapUserDetailsActivity;
import com.arsalan.garage.activities.ScrapUserListActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.vo.ScrapUserListData;
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
public class ScrapUserListFragment extends UserListBaseFragment {

    private static final String TAG = "ScrapUserListFragment";
    private ScrapUserListData mScrapUserListData;

    public ScrapUserListFragment() {
    }


    @Override
    protected String getListDownloadUrl() {
        /*Bundle bundle = getArguments();
        String url = bundle.getString(AppConstants.URL) + "/" + PrefUtility.getAccessToken();
        String pageLimitUrl = url + "?page=" + (++pageNumber) + "?limit=" + AppConstants.REQUEST_ITEM_COUNT;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + pageLimitUrl);
        httpRequest.setUrl(pageLimitUrl);
        */

        Bundle bundle = getArguments();
        String url = bundle.getString(AppConstants.URL) + "/" + PrefUtility.getAccessToken();
        return url;
    }

    @Override
    protected void setListData(ValueObject valueObject, JSONArray resultsJsonArray) {
        mScrapUserListData = (ScrapUserListData) valueObject;

        try {
            int size = resultsJsonArray.length();
            ArrayList<UserListItem> mForSaleItems = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                JSONObject jsonObject = resultsJsonArray.getJSONObject(i);
                ScrapUserListData.ScrapUserListItem scrapUserListItem = new ScrapUserListData.ScrapUserListItem();
                scrapUserListItem.setScrap_id(jsonObject.optString(AppConstants.SCRAP_ID));
                setUserItemValues(jsonObject, scrapUserListItem);
                mForSaleItems.add(scrapUserListItem);
            }

            mScrapUserListData.setResults(mForSaleItems);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mScrapUserListData == null) {
            return;
        }else if(mScrapUserListData.getResults() == null){
            return;
        }
        showData(mScrapUserListData.getResults());
        if (isFirstTime) {
            try{
                mTotalItemCount = Integer.parseInt(mScrapUserListData.getData_count());
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            setTotalCount(String.valueOf(mTotalItemCount));
            isFirstTime = false;
        }
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return ScrapUserListData.class.getName();
    }

    @Override
    protected Class getDetailsActivityClass() {
        return ScrapUserDetailsActivity.class;
    }

    private void setTotalCount(String totalCount) {
        try {
            ((ScrapUserListActivity) getActivity()).setNoOfItemsInTooBar((Integer.parseInt(totalCount)));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
