package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageHolderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageHolderFragment extends Fragment {

    private int mImageResId;

    public static ImageHolderFragment newInstance(int imageResId) {
        ImageHolderFragment fragment = new ImageHolderFragment();
        Bundle args = new Bundle();
        args.putInt(AppConstants.IMAGE_RESOURCE_ID, imageResId);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageHolderFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageResId = getArguments().getInt(AppConstants.IMAGE_RESOURCE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_holder, container, false);
        //ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(mImageResId, (ImageView)rootView.findViewById(R.id.imageview_car));
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageview_car);
        imageView.setImageResource(mImageResId);
        return rootView;
    }


}
