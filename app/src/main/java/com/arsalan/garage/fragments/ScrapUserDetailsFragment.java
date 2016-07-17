package com.arsalan.garage.fragments;

import android.content.Intent;
import android.util.Log;

import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.vo.ScrapUserDetailsData;

import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ScrapUserDetailsFragment extends UserDetailsBaseFragment {

    private String TAG = "ScrapUserDetailsFragment";
    private ScrapUserDetailsData mScrapUserDetailsData;

    public ScrapUserDetailsFragment() {
    }


    @Override
    protected void openFullImageActivity() {
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, getDetailsDownloadUrl());
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_SCRAP_USER);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerItemImages.getCurrentItem());
        startActivity(intent);
    }

    @Override
    protected String getCategory() {
        return AppConstants.SCRAP;
    }

    @Override
    protected String getItemId() {
        return mScrapUserDetailsData.getResults().getScrap_id();
    }

    @Override
    protected String getDeleteUrl() {
        String deleteUrl = Urls.SCRAP_DELETE + "?device_phone=" + PrefUtility.getAccessToken() + "&delete_id=" + getItemId();
        Log.e(TAG, " ******^^^^^^^^^Delete URL:" + deleteUrl);
        return deleteUrl;
    }

    @Override
    protected String getDetailsDownloadUrl() {
        String itemId = getArguments().getString(AppConstants.ID);
        String detailsDownloadUrl = Urls.SCRAP_USER_DETAILS + itemId + "/" + PrefUtility.getAccessToken();
        Log.e(TAG, " ******^^^^^^^^^DetailsDownload URL:" + detailsDownloadUrl);
        return detailsDownloadUrl;
    }

    @Override
    protected void setDetails(ValueObject valueObject) {
        mScrapUserDetailsData = (ScrapUserDetailsData) valueObject;
        mUserDetailsBase = mScrapUserDetailsData.getResults();
        setPagerAdapter();
        setDescription(mUserDetailsBase);
    }

    @Override
    protected String getValueObjectFullyQualifiedName() {
        return ScrapUserDetailsData.class.getName();
    }
}
