package com.arsalan.garage.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.arsalan.garage.R;
import com.arsalan.garage.uicomponents.ZoomableImageView;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Utils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageViewerActivity extends BaseActivity {
    private ZoomableImageView mZoomableImageView;
    private ProgressBar mProgressBarLoading;
    //private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        // ApptimizeUtils apptimizeUtils = new ApptimizeUtils(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getIntent().hasExtra(AppConstants.EXTRA_TITLE)) {
            setToolbar(toolbar, getIntent().getStringExtra(AppConstants.EXTRA_TITLE), true);
        }
        linkViewsId();
        if (getIntent().hasExtra(AppConstants.EXTRA_IMAGE_PATH)) {
            if (Utils.isNetworkAvailable(this)) {
                showImage(getIntent().getStringExtra(AppConstants.EXTRA_IMAGE_PATH));
            } else {
                Utils.showToastMessage(this, getResources().getString(R.string.no_internet_connection));
                finish();
            }
        } else {
            finish();
        }
    }

    private void linkViewsId() {
        mZoomableImageView = (ZoomableImageView) findViewById(R.id.imageview_profile_pic);
        mProgressBarLoading = (ProgressBar) findViewById(R.id.progressbar_loading);
    }

    private void showImage(String imageUrl) {
        Logger.i("imageUrl", imageUrl);
        Utils.setImageFromUrl(imageUrl, mZoomableImageView, R.drawable.ic_placeholder_item, mImageLoadingListener);
    }

    private ImageLoadingListener mImageLoadingListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            FailReason.FailType failType = failReason.getType();
            switch (failType) {
                case IO_ERROR:
                    showMessage(getString(R.string.error_out_of_memory));
                    break;
                case DECODING_ERROR:
                    showMessage(getString(R.string.msg_5xx));
                    break;
                case NETWORK_DENIED:
                    showMessage(getString(R.string.no_internet_connection));
                    break;
                case OUT_OF_MEMORY:
                    showMessage(getString(R.string.error_out_of_memory));
                    break;
                case UNKNOWN:
                    showMessage(getString(R.string.msg_5xx));
                    break;
            }
            finish();
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            mProgressBarLoading.setVisibility(View.GONE);
            mZoomableImageView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
        }
    };

    private void showMessage(String message) {
        Utils.showToastMessage(this, message);
    }
}