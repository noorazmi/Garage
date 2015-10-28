package com.arsalan.garage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionCarsViewPagerAdapter;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionTabViewPagerAdapter;
import com.arsalan.garage.utils.AppConstants;

/**
 * <p/>
 * Created by: Noor  Alam on 18/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencyDescriptionFragment extends Fragment {

    private ViewPager mViewpagerDescription;
    private TabLayout mTablayoutDescription;
    private ViewPager mViewPagerCarImages;
    private int[] mCarImagesResIds;

    public AlwakalatAgencyDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alwakalat_agency_description, container, false);
        mViewPagerCarImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mViewpagerDescription = (ViewPager) rootView.findViewById(R.id.viewpager_description);
        mTablayoutDescription = (TabLayout) rootView.findViewById(R.id.tablayout_description);
        mCarImagesResIds = new int[1];
        mCarImagesResIds[0] = getArguments().getInt(AppConstants.IMAGE_URL);
        setPagerAdapter();
        setDecriptionPagerAdapter();
        return rootView;
    }

    private void setPagerAdapter() {
        AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), mCarImagesResIds);
        mViewPagerCarImages.setAdapter(adapter);
    }
    private void setDecriptionPagerAdapter(){
        AlwakalatAgencyDescriptionTabViewPagerAdapter seasonsFragmentStatePagerAdapter = new AlwakalatAgencyDescriptionTabViewPagerAdapter(getActivity(), getChildFragmentManager());
        mViewpagerDescription.setAdapter(seasonsFragmentStatePagerAdapter);
        mTablayoutDescription.setupWithViewPager(mViewpagerDescription);
    }

}
