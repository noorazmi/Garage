package com.arsalan.garage.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * <p/>
 * Created by: Noor  Alam on 11/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ZoomImageHolderFragment extends Fragment {

    private String mProductImageUrl;
    private PhotoViewAttacher mAttacher;

    public static ZoomImageHolderFragment newInstance(String mProductImageUrl) {
        ZoomImageHolderFragment fragment = new ZoomImageHolderFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.EXTRA_IMAGE_URL, mProductImageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public ZoomImageHolderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductImageUrl = getArguments().getString(AppConstants.EXTRA_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final PhotoView photoView = (PhotoView) inflater.inflate(R.layout.fragment_zoom_image_holder, container, false);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(mProductImageUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage != null) {
                    photoView.setImageBitmap(loadedImage);
                    mAttacher = new PhotoViewAttacher(photoView);
                    mAttacher.setOnPhotoTapListener(new PhotoTapListener());
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return photoView;
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {
        @Override
        public void onPhotoTap(View view, float x, float y) {
            if (getActivity() instanceof ProductGalleryFragment.TopBottomViewHideShowListener) {
                ((ProductGalleryFragment.TopBottomViewHideShowListener) getActivity()).setTopBottomViewVisibility();
            }
        }
    }
}
