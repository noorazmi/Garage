package com.arsalan.garage.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.util.Log;

import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.vo.ForSaleUserDetailsData;

import networking.models.ValueObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForSaleUserDetailsFragment extends UserDetailsBaseFragment {

    private String TAG = "ForSaleUserDetailsFragment";
    private ForSaleUserDetailsData mForSaleUserDetailsData;

    public ForSaleUserDetailsFragment() {
    }

    protected void openFullImageActivity(){
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, getDetailsDownloadUrl());
        //intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_SALE);
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_USER_GALLERY);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerItemImages.getCurrentItem());
        startActivity(intent);
    }

    @Override
    protected String getCategory() {
        return AppConstants.ACCESSORIES;
    }

    @Override
    protected String getItemId() {
        return mForSaleUserDetailsData.getResults().getForsale_id();
    }

    @Override
    protected String getDeleteUrl() {
        String deleteUrl = Urls.FORSALE_DELETE + "?device_phone=" + PrefUtility.getAccessToken() + "&delete_id=" + getItemId();
        Log.e(TAG, " ******^^^^^^^^^Delete URL:" + deleteUrl);
        return deleteUrl;
    }

    @Override
    protected String getDetailsDownloadUrl() {
        String itemId = getArguments().getString(AppConstants.EXTRA_FORSALE_ID);
        String detailsDownloadUrl = Urls.FOR_SALE_USER_DETAILS + itemId + "/" + PrefUtility.getAccessToken();
        Log.e(TAG, " ******^^^^^^^^^DetailsDownload URL:" + detailsDownloadUrl);
        return detailsDownloadUrl;
    }

    @Override
    protected void setDetails(ValueObject valueObject) {
        mForSaleUserDetailsData = (ForSaleUserDetailsData) valueObject;
        mUserDetailsBase = mForSaleUserDetailsData.getResults();
        setPagerAdapter();
        setDescription(mUserDetailsBase);
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return ForSaleUserDetailsData.class.getName();
    }


}
