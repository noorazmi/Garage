package com.arsalan.garage.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.BuyAndSaleDescPagerAdapter;
import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.BuyAndSaleDescVo;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyAndSaleDescriptionFragment extends Fragment {

    private ViewPager mViewpagerDescription;
    //private TabLayout mTablayoutDescription;
    private ViewPager mViewPagerCarImages;
    private String TAG = "BuyAndSaleDescriptionFragment";
    private BuyAndSaleDescVo mBuyAndSaleDescVo;
    private GestureDetector mGestureDetector;
    //private int mCurrentCarIndex = 0;

    private TextView mTextViewDescription;
    private TextView mTextViewPhone;
    private TextView mTextViewPhone1;
    private TextView mTextViewPhone2;

    public BuyAndSaleDescriptionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buy_and_sale_description, container, false);

        mTextViewDescription = (TextView) rootView.findViewById(R.id.textview_title_english);
        mTextViewDescription.setVisibility(View.VISIBLE);

        mTextViewPhone = (TextView) rootView.findViewById(R.id.textview_phone_number);
        mTextViewPhone1 = (TextView) rootView.findViewById(R.id.textview_phone_number1);
        mTextViewPhone2 = (TextView) rootView.findViewById(R.id.textview_phone_number2);

        mViewPagerCarImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mGestureDetector = new GestureDetector(getActivity(), mSimpleOnGestureListener);
        mViewPagerCarImages.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        mViewpagerDescription = (ViewPager) rootView.findViewById(R.id.viewpager_description);
        //mTablayoutDescription = (TabLayout) rootView.findViewById(R.id.tablayout_description);
        if(Utils.isNetworkAvailable(getActivity())){
            performGET();
        }else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    private void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = mBuyAndSaleDescVo.getResults().getImages();
        BuyAndSaleDescPagerAdapter adapter = new BuyAndSaleDescPagerAdapter(getFragmentManager(), carImageArrayList);
        mViewPagerCarImages.setAdapter(adapter);
    }

    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //Bundle bundle = getArguments();
        String url = Urls.FORSALE_DESCRIPTION_BASE + getArguments().getString(AppConstants.EXTRA_FORSALE_ID) + GarageApp.DEVICE_UUID_WITH_SLASH;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + url);
        httpRequest.setUrl(url);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(BuyAndSaleDescVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mBuyAndSaleDescVo = (BuyAndSaleDescVo) httpResponse.getValueObject();
                setPagerAdapter();
                setValuesInUI(mBuyAndSaleDescVo);
            }
        });
        loaderHandler.loadData();
    }


    private void setValuesInUI(BuyAndSaleDescVo valueObject){
        if(valueObject == null){
            return;
        }
        mTextViewDescription.setText(valueObject.getResults().getDescription());

        if(TextUtils.isEmpty(valueObject.getResults().getPhone())){
            mTextViewPhone.setVisibility(View.GONE);
        }else {
            mTextViewPhone.setVisibility(View.VISIBLE);
            mTextViewPhone.setText(valueObject.getResults().getPhone());

        }
        if(TextUtils.isEmpty(valueObject.getResults().getPhone())){
            mTextViewPhone1.setVisibility(View.GONE);
        }else {
            mTextViewPhone1.setVisibility(View.VISIBLE);
            mTextViewPhone1.setText(valueObject.getResults().getMake_region_name());

        }

        if(TextUtils.isEmpty(valueObject.getResults().getMake())){
            mTextViewPhone2.setVisibility(View.GONE);
        }else {
            mTextViewPhone2.setVisibility(View.VISIBLE);
            mTextViewPhone2.setText(valueObject.getResults().getMake());

        }
    }


    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener =  new  GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            openFullImageActivity();
            return super.onSingleTapConfirmed(e);
        }
    };


    private void openFullImageActivity(){
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, Urls.FORSALE_DESCRIPTION_BASE + getArguments().getString(AppConstants.EXTRA_FORSALE_ID));
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_MARINE_SHOWROOM);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerCarImages.getCurrentItem());
        startActivity(intent);
    }


}
