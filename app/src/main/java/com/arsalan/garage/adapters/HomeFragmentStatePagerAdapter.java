package com.arsalan.garage.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arsalan.garage.fragments.HomeTabFragment;
import com.arsalan.garage.fragments.PostAdTabFragment;
import com.arsalan.garage.fragments.SettingTabFragment;


/**
 * <p/>
 * Project: <b>FamePlus</b><br/>
 * Created by: Noor  Alam on 10/07/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class HomeFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_COUNT = 3;
    private static final int POSITION_DAILY = 0;
    private static final int POSITION_MONTHLY = 1;
    private static final int POSITION_SEASON = 2;
    private Context mContex;
    //private AboutFtlModel mAboutFtlModel;
    //private int[] mPageTitles = {R.string.about_ftl, R.string.leader_board, R.string.contest, R.string.rules, R.string.settings_terms_and_conditions};


    public HomeFragmentStatePagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContex = context;
        //this.mAboutFtlModel = mAboutFtlModel;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {

            case POSITION_DAILY:
                fragment = new HomeTabFragment();
                break;
            case POSITION_MONTHLY:
                fragment = new PostAdTabFragment();
                break;
            case POSITION_SEASON:
                fragment = new SettingTabFragment();
                break;

            default:
                break;


        }
        return fragment;
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mContex.getString(mPageTitles[position]);
//    }
}
