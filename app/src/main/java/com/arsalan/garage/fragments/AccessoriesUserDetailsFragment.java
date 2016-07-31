package com.arsalan.garage.fragments;

import android.content.Intent;
import android.util.Log;

import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.models.AccessoriesUserDetailsData;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesUserDetailsFragment extends UserDetailsBaseFragment {

    private String TAG = "AccessoriesUserDescriptionFra";
    private AccessoriesUserDetailsData mAccessoriesUserDetailsData;

    public AccessoriesUserDetailsFragment() {
    }

    @Override
    protected void openFullImageActivity() {
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, getDetailsDownloadUrl());
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerItemImages.getCurrentItem());
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_USER_GALLERY);
        startActivity(intent);
    }

    @Override
    protected String getCategory() {
        return AppConstants.ACCESSORIES;
    }

    @Override
    protected String getItemId() {
        return mAccessoriesUserDetailsData.getResults().getAccessories_id();
    }

    @Override
    protected String getDeleteUrl() {
        String deleteUrl = Urls.ACCESSORIES_DELETE + "?device_phone=" + PrefUtility.getAccessToken() + "&delete_id=" + getItemId();
        Log.e(TAG, " ******^^^^^^^^^Delete URL:" + deleteUrl);
        return deleteUrl;
    }

    @Override
    protected String getDetailsDownloadUrl() {
        String itemId = getArguments().getString(AppConstants.ID);
        String detailsDownloadUrl = Urls.ACCESSORIES_USER_DETAILS + itemId + "/" + PrefUtility.getAccessToken();
        Log.e(TAG, " ******^^^^^^^^^DetailsDownload URL:" + detailsDownloadUrl);
        return detailsDownloadUrl;
    }

    @Override
    protected void setDetails(ValueObject valueObject) {
        mAccessoriesUserDetailsData = (AccessoriesUserDetailsData) valueObject;
        mUserDetailsBase = mAccessoriesUserDetailsData.getResults();
        setPagerAdapter();
        setDescription(mUserDetailsBase);
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return AccessoriesUserDetailsData.class.getName();
    }
}
