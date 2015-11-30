package com.arsalan.garage.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.adapters.HomeFragmentStatePagerAdapter;

public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private boolean isAboutFTLFragmentActive = true;
    private int[] mPageTitles = {R.string.home, R.string.post_add, R.string.setting};
    private TextView[] mTabTextViews;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager_season);
        mViewPager.setOffscreenPageLimit(HomeFragmentStatePagerAdapter.PAGE_COUNT);
        HomeFragmentStatePagerAdapter homeFragmentStatePagerAdapter = new HomeFragmentStatePagerAdapter(getActivity(), getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(homeFragmentStatePagerAdapter);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        setTabs();
        mTabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);

                        resetTitleColor();
                        mTabTextViews[tab.getPosition()].setTextColor(getResources().getColor(android.R.color.black));

                        if (tab.getPosition() == 0) {
                            isAboutFTLFragmentActive = true;
                        } else {
                            isAboutFTLFragmentActive = false;

                        }

                    }
                });

        return rootView;
    }

    private void setTabs() {
        int tabCount = mTabLayout.getTabCount();
        mTabTextViews = new TextView[tabCount];
        for (int i = 0; i < tabCount; i++) {
            TextView tv = getTextView(mPageTitles[i], android.R.color.darker_gray);
            if (i == 0) {
                tv.setTextColor(getResources().getColor(android.R.color.black));
            }
            mTabLayout.getTabAt(i).setCustomView(tv);
            mTabTextViews[i] = tv;
        }
    }

    private TextView getTextView(int title, int color) {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tv = (TextView) layoutInflater.inflate(R.layout.textview_tab, null);
        tv.setText(getString(title));
        tv.setTextColor(getResources().getColor(color));
        return tv;
    }

    private void resetTitleColor() {
        for (int i = 0; i < mTabTextViews.length; i++) {
            mTabTextViews[i].setTextColor(getResources().getColor(R.color.grey));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getView().setFocusableInTouchMode(true);
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mViewPager.getCurrentItem() != 0) {
                        mViewPager.setCurrentItem(0, true);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
