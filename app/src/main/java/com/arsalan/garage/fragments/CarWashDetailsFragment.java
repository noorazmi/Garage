package com.arsalan.garage.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionCarsViewPagerAdapter;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.CarWashDetailsVO;
import com.arsalan.garage.vo.ForSaleUserDetailsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;
import networking.models.ValueObject;

/**
 * <p/>
 * Created by: Noor  Alam on 06/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CarWashDetailsFragment extends Fragment {

    private String TAG = "CarWashDetailsFragment";
    private CarWashDetailsVO mCarWashDetailsVO;
    protected ViewPager mViewPagerItemImages;
    protected GestureDetector mGestureDetector;
    protected TextView mTextviewDescription;
    protected TextView mTextviewPhone1;
    protected TextView mTextviewPhone2;
    protected AlertDialog mShareOptionsAlertDialog;
    protected String mShareText;
    protected TextView mTextViewModel;
    protected CarWashDetailsVO.Results mResults;
    private List<ImageView> indicatorImage;
    private LinearLayout indicatorLayout;

    public CarWashDetailsFragment() {
    }

    protected void openFullImageActivity() {
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, getDetailsDownloadUrl());
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_CAR_WASH);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerItemImages.getCurrentItem());
        startActivity(intent);
    }

    protected String getDetailsDownloadUrl() {
        String itemId = getArguments().getString(AppConstants.ID);
        String detailsDownloadUrl = Urls.MOVABLE_WASH + "/" + itemId;
        Log.e(TAG, " ******^^^^^^^^^DetailsDownload URL:" + detailsDownloadUrl);
        return detailsDownloadUrl;
    }

    protected void setDetails(ValueObject valueObject) {
        mCarWashDetailsVO = (CarWashDetailsVO) valueObject;
        mResults = mCarWashDetailsVO.getResults();
        setPagerAdapter();
        setDescription(mResults);
    }

    protected String getValueObjectFullyQualifiedName() {
        return ForSaleUserDetailsData.class.getName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carwash_details, container, false);
        indicatorLayout = (LinearLayout) rootView.findViewById(R.id.pager_indicator_layout);
        mViewPagerItemImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mTextviewDescription = (TextView) rootView.findViewById(R.id.textview_description);
        mTextviewPhone1 = (TextView) rootView.findViewById(R.id.textview_phone1);
        mTextviewPhone1.setVisibility(View.VISIBLE);
        mTextviewPhone2 = (TextView) rootView.findViewById(R.id.textview_phone2);
        mTextViewModel = (TextView) rootView.findViewById(R.id.textview_model);
        mGestureDetector = new GestureDetector(getActivity(), mSimpleOnGestureListener);
        mViewPagerItemImages.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        mViewPagerItemImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (indicatorImage != null && indicatorImage.size() > 0) {
                    position = position % indicatorImage.size();
                    Utils.setIndicator(position, indicatorImage);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        if (Utils.isNetworkAvailable(getActivity())) {
            performGET();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    protected void setDescription(final CarWashDetailsVO.Results userDetailsBase) {
        mShareText = userDetailsBase.getDescription();
        mTextViewModel.setText(userDetailsBase.getName());
        mTextviewDescription.setText(mShareText);
        if (userDetailsBase.getPhone() != null) {
            for (int i = 0; i < userDetailsBase.getPhone().size(); i++) {
                if (i == 0) {
                    mTextviewPhone1.setText(userDetailsBase.getPhone().get(i));
                    mTextviewPhone1.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    mTextviewPhone2.setText(userDetailsBase.getPhone().get(i));
                    mTextviewPhone2.setVisibility(View.VISIBLE);
                }
            }
        }
        mTextviewPhone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.initCall(mTextviewPhone1.getText().toString(), getActivity());
            }
        });
        mTextviewPhone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.initCall(mTextviewPhone1.getText().toString(), getActivity());
            }
        });

    }

    protected GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            openFullImageActivity();
            return super.onSingleTapConfirmed(e);
        }
    };


    protected void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = null;
        if (mResults != null) {
            carImageArrayList = (ArrayList<ImageInfo>) mResults.getImages();
            AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
            mViewPagerItemImages.setAdapter(adapter);
            indicatorImage = Utils.getCircleIndicator(getActivity(), carImageArrayList.size(), indicatorLayout);
            Utils.setIndicator(0, indicatorImage);
            if (carImageArrayList.size() <= 1) {
                indicatorLayout.setVisibility(View.INVISIBLE);
            } else {
                indicatorLayout.setVisibility(View.VISIBLE);
            }
        }

    }

    protected void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String fullUrl = getDetailsDownloadUrl();
        httpRequest.setUrl(fullUrl);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(getValueObjectFullyQualifiedName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                String responseString = httpResponse.getResponseJSONString();
                if (responseString == null || responseString.contains("fail")) {
                    showFailMessage(getString(R.string.error_something_went_wrong));
                    return;
                }
                mCarWashDetailsVO = getCarWashDetailsVo(responseString);
                setDetails(mCarWashDetailsVO);
                getActivity().invalidateOptionsMenu();
            }
        });
        loaderHandler.loadData();
    }

    public static CarWashDetailsVO getCarWashDetailsVo(String jsonString) {
        CarWashDetailsVO carWashDetailsVO = new CarWashDetailsVO();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            carWashDetailsVO.setMessage(jsonObject.getString("message"));
            carWashDetailsVO.setStatus(jsonObject.getString("status"));

            JSONObject resultJsonObject = jsonObject.getJSONObject("results");
            CarWashDetailsVO.Results results = new CarWashDetailsVO.Results();
            results.setCarWashId(resultJsonObject.getInt("car_wash_id"));
            results.setName(resultJsonObject.getString("name"));
            results.setDescription(resultJsonObject.getString("description"));

            JSONArray phoneJsonArray = resultJsonObject.getJSONArray("phone");
            ArrayList<String> phones = new ArrayList<>();
            for (int i = 0; i < phoneJsonArray.length(); i++) {
                phones.add((String) phoneJsonArray.get(i));
            }
            results.setPhone(phones);


            JSONArray imageJsonArray = resultJsonObject.getJSONArray("images");
            ArrayList<ImageInfo> images = new ArrayList<>();
            for (int i = 0; i < imageJsonArray.length(); i++) {
                ImageInfo imageInfo = new ImageInfo();
                JSONObject imageJsonObject = (JSONObject) imageJsonArray.get(i);
                imageInfo.setPhoto_id(imageJsonObject.getString("photo_id"));
                imageInfo.setPhoto_name(imageJsonObject.getString("photo_name"));
                images.add(imageInfo);
            }
            results.setImages(images);
            carWashDetailsVO.setResults(results);
            return carWashDetailsVO;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showFailMessage(String message) {
        Utils.showSnackBar(getActivity(), message);
        mViewPagerItemImages.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        }, 2000);
    }


}
