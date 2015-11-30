package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arsalan.garage.R;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZoomImageFragment extends Fragment {

    private String  mCarImageUrl;

    public static ZoomImageFragment newInstance(ImageInfo carImage) {
        ZoomImageFragment fragment = new ZoomImageFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.EXTRA_IMAGE_URL, carImage.getPhoto_name());
        fragment.setArguments(args);
        return fragment;
    }

    public ZoomImageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCarImageUrl = getArguments().getString(AppConstants.EXTRA_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zoom_image, container, false);
        //ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(mImageResId, (ImageView)rootView.findViewById(R.id.imageview_car));
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageview_car);
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!TextUtils.isEmpty(mCarImageUrl)){
            imageLoader.displayImage(mCarImageUrl, imageView, Utils.gerDisplayImageOptions());
        }
        return rootView;
    }


}
