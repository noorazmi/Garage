package com.arsalan.garage.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ContactFragment;
import com.arsalan.garage.fragments.ModelFragment;
import com.arsalan.garage.fragments.OfferFragment;

/**
 * <p/>
 * Created by: Noor  Alam on 27/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencyDescriptionTabViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_COUNT = 3;
    private static final int POSITION_MODEL = 0;
    private static final int POSITION_OFFER = 1;
    private static final int POSITION_CONTACT = 2;
    private Context mContex;
    private int[] mPageTitles = {R.string.model, R.string.offer, R.string.contact};

    public AlwakalatAgencyDescriptionTabViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mContex = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {

            case POSITION_MODEL:
                fragment = new ModelFragment();
                break;
            case POSITION_OFFER:
                fragment = new OfferFragment();
                break;
            case POSITION_CONTACT:
                fragment = new ContactFragment();
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mContex.getString(mPageTitles[position]);
    }
}

