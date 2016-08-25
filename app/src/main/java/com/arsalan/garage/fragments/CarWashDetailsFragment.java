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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionCarsViewPagerAdapter;
import com.arsalan.garage.adapters.ShareListAdapter;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.models.ShareOptionItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.CustomDialogHelper;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.CarWashDetailsVO;
import com.arsalan.garage.vo.ForSaleUserDetailsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    protected String mShareImage;
    protected String mShareText;
    protected TextView mTextViewModel;
    protected ImageView mImageViewEmail;
    protected CarWashDetailsVO.Results mResults;

    public CarWashDetailsFragment() {
    }

    protected void openFullImageActivity(){
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carwash_details, container, false);
        mViewPagerItemImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mTextviewDescription = (TextView) rootView.findViewById(R.id.textview_description);
        mTextviewPhone1 = (TextView) rootView.findViewById(R.id.textview_phone1);
        mTextviewPhone1.setVisibility(View.VISIBLE);
        mTextviewPhone2 = (TextView) rootView.findViewById(R.id.textview_phone2);
        mTextViewModel = (TextView) rootView.findViewById(R.id.textview_model);
        mImageViewEmail = (ImageView) rootView.findViewById(R.id.imageview_email);
        mGestureDetector = new GestureDetector(getActivity(), mSimpleOnGestureListener);
        mViewPagerItemImages.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
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
        if(userDetailsBase.getPhone() != null){
            for (int i = 0; i < userDetailsBase.getPhone().size(); i++) {
                if(i ==0){
                    mTextviewPhone1.setText(userDetailsBase.getPhone().get(i));
                    mTextviewPhone1.setVisibility(View.VISIBLE);
                }
                if(i == 1){
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

        mImageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.shareOnGmail(getActivity(), mShareText, mShareImage);
            }
        });
    }

    protected GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener =  new  GestureDetector.SimpleOnGestureListener(){

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            openFullImageActivity();
            return super.onSingleTapConfirmed(e);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                showShareOptions();
                break;
            default:
                break;
        }
        return false;
    }

    protected void showShareOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ListView mBankListView = new ListView(getActivity());

        final ShareListAdapter shareListAdapter = new ShareListAdapter(getActivity(), Utils.getShareOptions());
        mBankListView.setAdapter(shareListAdapter);
        mBankListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ShareOptionItem shareOptionItem = (ShareOptionItem) shareListAdapter.getItem(position);
                mShareOptionsAlertDialog.dismiss();
                switch (position) {
                    case 0://facebook
                        ShareUtil.shareOnFacebook(getActivity(), mShareText, mShareImage);
                        break;
                    case 1://twitter
                        ShareUtil.shareOnTwitter(getActivity(), mShareText, mShareImage);
                        break;
                    case 2://whatsapp
                        ShareUtil.shareOnWhatsApp(getActivity(), mShareText, mShareImage);
                        break;
                    default:
                        break;

                }
            }
        });
        builder.setView(mBankListView);
        builder.setTitle(getActivity().getString(R.string.select_sharing_option));
        mShareOptionsAlertDialog = builder.create();
        CustomDialogHelper customDialogHelper = new CustomDialogHelper(getActivity());
        mShareOptionsAlertDialog.show();
        customDialogHelper.changeDialog(mShareOptionsAlertDialog);
    }

    protected void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = null;
        if (mResults != null) {
            carImageArrayList = (ArrayList<ImageInfo>) mResults.getImages();
            if(carImageArrayList.size() > 0){
                mShareImage = carImageArrayList.get(0).getPhoto_name();
            }
            AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
            mViewPagerItemImages.setAdapter(adapter);
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
                if(responseString == null || responseString.contains("fail")){
                    showFailMessage(getString(R.string.error_something_went_wrong));
                    return;
                }
                mCarWashDetailsVO =  getCarWashDetailsVo(responseString);
                setDetails(mCarWashDetailsVO);
                getActivity().invalidateOptionsMenu();
            }
        });
        loaderHandler.loadData();
    }

    public static CarWashDetailsVO getCarWashDetailsVo(String jsonString){
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

    private void showFailMessage(String message){
        Utils.showSnackBar(getActivity(), message);
        mViewPagerItemImages.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().finish();
            }
        }, 2000);
    }




}
