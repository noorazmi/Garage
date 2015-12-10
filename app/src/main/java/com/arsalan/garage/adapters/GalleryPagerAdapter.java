package com.arsalan.garage.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.arsalan.garage.fragments.ZoomImageHolderFragment;
import com.arsalan.garage.models.ImageInfo;

import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 04/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private List<ImageInfo> mProductImagesList;

    public GalleryPagerAdapter(List<ImageInfo> productImagesList, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mProductImagesList = productImagesList;
    }

    @Override
    public int getCount() {
        return mProductImagesList.size();
    }

    @Override
    public Fragment getItem(int position) {
        ZoomImageHolderFragment sampleFragment = ZoomImageHolderFragment.newInstance(mProductImagesList.get(position).getPhoto_name());
        return sampleFragment;
    }
}
