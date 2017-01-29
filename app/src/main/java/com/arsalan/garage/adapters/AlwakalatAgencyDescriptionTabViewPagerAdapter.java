package com.arsalan.garage.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.ContactFragment;
import com.arsalan.garage.fragments.DetailsFragment;
import com.arsalan.garage.fragments.FeaturesFragment;
import com.arsalan.garage.fragments.WarrantyFragment;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.ShowroomCarVo;

/**
 * <p/>
 * Created by: Noor  Alam on 27/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencyDescriptionTabViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_COUNT = 4;
    private static final int POSITION_CONTACT_US = 0;
    private static final int POSITION_WARRANTY = 1;
    private static final int POSITION_FEATURES = 2;
    private static final int POSITION_DETAILS = 3;
    private Context mContex;
    private int[] mPageTitles = { R.string.contact_us_ar, R.string.warranty, R.string.feature, R.string.details};
    private ShowroomCarVo mShowroomCarVo;

    public AlwakalatAgencyDescriptionTabViewPagerAdapter(Context context, FragmentManager fragmentManager, ShowroomCarVo showroomCarVo) {
        super(fragmentManager);
        this.mContex = context;
        this.mShowroomCarVo = showroomCarVo;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position) {

            case POSITION_CONTACT_US:
                fragment = new ContactFragment();
                //bundle.putString(AppConstants.EXTRA_CONTACT, );
                bundle.putStringArray(AppConstants.EXTRA_CONTACT, getPhoneNumbers());
                bundle.putString(AppConstants.EXTRA_CONTACT_INFO,mShowroomCarVo.getResults().getContact().split(":")[0]);
                break;
            case POSITION_WARRANTY:
                fragment = new WarrantyFragment();
                bundle.putString(AppConstants.EXTRA_WARRANTY, mShowroomCarVo.getResults().getWarranty());
                break;
            case POSITION_FEATURES:
                fragment = new FeaturesFragment();
                bundle.putString(AppConstants.EXTRA_DESCRIPTION, mShowroomCarVo.getResults().getDescription());
                break;
            case POSITION_DETAILS:
                fragment = new DetailsFragment();
                bundle.putString(AppConstants.EXTRA_YEAR, mShowroomCarVo.getResults().getYear());
                bundle.putString(AppConstants.EXTRA_ENGINE, mShowroomCarVo.getResults().getEngine());
                bundle.putString(AppConstants.EXTRA_TRANSMISSION, mShowroomCarVo.getResults().getTransmission());
                bundle.putString(AppConstants.EXTRA_PAYMENT, mShowroomCarVo.getResults().getPayment());
                bundle.putString(AppConstants.EXTRA_PRICE, mShowroomCarVo.getResults().getPrice());
                break;
            default:
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    private String[] getPhoneNumbers(){
        String[] phoneNumbers = new String[mShowroomCarVo.getResults().getPhones().length + 1];
        String contact1 = mShowroomCarVo.getResults().getContact().split(":")[1];
        String[] phones = mShowroomCarVo.getResults().getPhones();
        phoneNumbers[0] = contact1;
        for (int i = 0; i < phones.length; i++) {
            phoneNumbers[i+1] = phones[i];
        }
        return phoneNumbers;
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

