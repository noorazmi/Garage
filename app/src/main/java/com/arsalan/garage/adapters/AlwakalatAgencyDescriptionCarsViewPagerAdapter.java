package com.arsalan.garage.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arsalan.garage.fragments.ImageHolderFragment;

/**
 * <p/>
 * Created by: Noor  Alam on 26/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencyDescriptionCarsViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "AlwakalatAgencyDescriptionCarsViewPagerAdapter";
    private int[] mCarImagesResIds;
    private FragmentManager mFragmentManager;

    public AlwakalatAgencyDescriptionCarsViewPagerAdapter(FragmentManager fm, int[] carImagesResIds) {
        super(fm);
        this.mFragmentManager = fm;
        this.mCarImagesResIds = carImagesResIds;
    }

    @Override
    public int getCount() {
        return mCarImagesResIds.length;
    }

    @Override
    public Fragment getItem(int position) {
        //Create a new instance of the fragment and return it.
        ImageHolderFragment sampleFragment = (ImageHolderFragment) ImageHolderFragment.newInstance(mCarImagesResIds[position]);
        return sampleFragment;
    }
}

