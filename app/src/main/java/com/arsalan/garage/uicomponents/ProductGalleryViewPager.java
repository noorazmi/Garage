package com.arsalan.garage.uicomponents;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.arsalan.garage.utils.Logger;

/**
 * <p/>
 * Created by: Noor  Alam on 08/12/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ProductGalleryViewPager extends ViewPager {


    public ProductGalleryViewPager(Context context) {
        super(context);
    }

    public ProductGalleryViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Logger.e(ProductGalleryViewPager.class.getSimpleName(), "***** Exception captured and suppressed ******");
            e.printStackTrace();
            return false;
        }
    }
}
