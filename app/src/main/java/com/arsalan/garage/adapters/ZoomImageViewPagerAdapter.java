package com.arsalan.garage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arsalan.garage.fragments.ZoomImageFragment;
import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 23/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ZoomImageViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "ZoomImageViewPagerAdapter";
    private FragmentManager mFragmentManager;
    private ArrayList<ImageInfo> mCarImageArrayList;

    public ZoomImageViewPagerAdapter(FragmentManager fm, ArrayList<ImageInfo> carImageArrayList) {
        super(fm);
        this.mFragmentManager = fm;
        this.mCarImageArrayList = carImageArrayList;
    }

    @Override
    public int getCount() {
        return mCarImageArrayList.size();
    }

    @Override
    public Fragment getItem(int position) {
        ZoomImageFragment sampleFragment = (ZoomImageFragment) ZoomImageFragment.newInstance(mCarImageArrayList.get(position));
        return sampleFragment;
    }
}
