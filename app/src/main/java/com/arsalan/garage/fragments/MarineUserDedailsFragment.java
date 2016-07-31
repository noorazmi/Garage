package com.arsalan.garage.fragments;

import android.content.Intent;
import android.util.Log;

import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.vo.MarineUserDetailsData;

import networking.models.ValueObject;

public class MarineUserDedailsFragment extends UserDetailsBaseFragment {

    private String TAG = "MarineUserDescriptionFragment";
    private MarineUserDetailsData mMarineUserDetailsData;

    public MarineUserDedailsFragment() {
    }


    @Override
    protected String getCategory() {
        return AppConstants.MARINE;
    }

    @Override
    protected String getItemId() {
        return mMarineUserDetailsData.getResults().getMarine_id();
    }

    @Override
    protected String getDeleteUrl() {
        String deleteUrl = Urls.MARINE_DELETE + "?device_phone=" + PrefUtility.getAccessToken() + "&delete_id=" + getItemId();
        Log.e(TAG, " ******^^^^^^^^^Delete URL:" + deleteUrl);
        return deleteUrl;
    }

    @Override
    protected String getDetailsDownloadUrl() {
        String itemId = getArguments().getString(AppConstants.ID);
        String detailsDownloadUrl = Urls.MARINE_USER_DETAILS + itemId + "/" + PrefUtility.getAccessToken();
        Log.e(TAG, " ******^^^^^^^^^DetailsDownload URL:" + detailsDownloadUrl);
        return detailsDownloadUrl;
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return MarineUserDetailsData.class.getName();
    }

    @Override
    protected void setDetails(ValueObject valueObject) {
        mMarineUserDetailsData = (MarineUserDetailsData) valueObject;
        mUserDetailsBase = mMarineUserDetailsData.getResults();
        setPagerAdapter();
        setDescription(mUserDetailsBase);
    }

    @Override
    protected void openFullImageActivity() {
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, getDetailsDownloadUrl());
        //intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_MARINE_USER);
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_USER_GALLERY);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerItemImages.getCurrentItem());
        startActivity(intent);
    }
}
