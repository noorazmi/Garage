package com.arsalan.garage.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.arsalan.garage.fragments.ImageHolderFragment;
import com.arsalan.garage.models.ImageInfo;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class BuyAndSaleDescPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "BuyAndSaleDescPagerAdapter";
    private FragmentManager mFragmentManager;
    private ArrayList<ImageInfo> mCarImageArrayList;

    public BuyAndSaleDescPagerAdapter(FragmentManager fm, ArrayList<ImageInfo> carImageArrayList) {
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
        //Create a new instance of the fragment and return it.
        ImageHolderFragment sampleFragment = (ImageHolderFragment) ImageHolderFragment.newInstance(mCarImageArrayList.get(position));
        return sampleFragment;
    }
}

