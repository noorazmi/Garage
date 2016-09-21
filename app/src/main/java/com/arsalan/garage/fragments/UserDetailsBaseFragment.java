package com.arsalan.garage.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.EditPostActivity;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionCarsViewPagerAdapter;
import com.arsalan.garage.adapters.ShareListAdapter;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.models.ShareOptionItem;
import com.arsalan.garage.models.UserDetailsBase;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.CustomDialogHelper;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.BaseVO;

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
 * Created by: Noor  Alam on 14/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public abstract class UserDetailsBaseFragment extends Fragment{

    protected ViewPager mViewPagerItemImages;
    protected GestureDetector mGestureDetector;
    protected TextView mTextviewDescription;
    protected TextView mTextviewPhone;
    protected AlertDialog mShareOptionsAlertDialog;
    protected String mShareImage;
    protected String mShareText;
    protected TextView mTextViewPrice;
    protected TextView mTextViewModel;
    protected TextView mTextViewTitle;
    protected ImageView mImageViewEmail;
    protected UserDetailsBase mUserDetailsBase;
    private LinearLayout indicatorLayout;
    private List<ImageView> indicatorImage;
    private static final int MY_PERMISSIONS_REQUEST_SMS = 89;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_description, container, false);
        indicatorLayout = (LinearLayout) rootView.findViewById(R.id.pager_indicator_layout);
        mViewPagerItemImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mTextviewDescription = (TextView) rootView.findViewById(R.id.textview_description);
        mTextviewPhone = (TextView) rootView.findViewById(R.id.textview_phone1);
        mTextViewModel = (TextView) rootView.findViewById(R.id.textview_model);
        mTextViewPrice = (TextView) rootView.findViewById(R.id.textview_price);
        mTextViewTitle = (TextView) rootView.findViewById(R.id.textview_title);
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
        return rootView;
    }

    protected void setDescription(final UserDetailsBase userDetailsBase) {
        mShareText = userDetailsBase.getDescription();
        mTextviewDescription.setText(mShareText);
        mTextViewTitle.setText(userDetailsBase.getTitle());
        mTextViewPrice.setText(userDetailsBase.getPrice());
        mTextViewModel.setText(userDetailsBase.getModel());
        mTextviewPhone.setText(userDetailsBase.getPhone());
        mTextviewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.initCall(mTextviewPhone.getText().toString(), getActivity());
            }
        });

        mImageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ShareUtil.shareOnGmail(getActivity(), mShareText, mShareImage);
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    grantSendSMSPermission();
                    return;
                }
                ShareUtil.sendSMS(getActivity(), mTextviewPhone.getText().toString(), mShareText);

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the camera-related task you need to do.
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void grantSendSMSPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block this thread waiting for the user's response! After the user sees the explanation, try again to request the permission.
                Toast.makeText(getActivity(), "Please grant the Send SMS permission by going in the App info", Toast.LENGTH_SHORT).show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SMS);
            }
        }
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
        if (mUserDetailsBase != null && mUserDetailsBase.getIs_owner() == 1) {
            menu.findItem(R.id.menu_item_delete).setVisible(true);
            menu.findItem(R.id.menu_item_edit).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                showShareOptions();
                break;
            case R.id.menu_item_delete:
                deleteItem();
                break;
            case R.id.menu_item_edit:
                Intent intent = new Intent(getActivity(), EditPostActivity.class);
                intent.putExtra(AppConstants.ID, getItemId());
                intent.putExtra(AppConstants.TITLE, mUserDetailsBase.getTitle());
                intent.putExtra(AppConstants.PHONE, mUserDetailsBase.getPhone());
                intent.putExtra(AppConstants.PRICE, mUserDetailsBase.getPrice());
                intent.putExtra(AppConstants.MODEL, mUserDetailsBase.getModel());
                intent.putExtra(AppConstants.DESCRIPTION, mUserDetailsBase.getDescription());
                intent.putExtra(AppConstants.CATEGORY, getCategory());
                intent.putExtra(AppConstants.SUB_CATEGORY, mUserDetailsBase.getMake_region_name());

                ArrayList<ImageInfo> carImageArrayList = mUserDetailsBase.getImages();
                ArrayList<String> imageUrls = new ArrayList<>(carImageArrayList.size());
                for (ImageInfo imageInfo : carImageArrayList) {
                    imageUrls.add(imageInfo.getPhoto_name());
                }
                intent.putStringArrayListExtra(AppConstants.IMAGE_LIST, imageUrls);
                startActivity(intent);
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

    private void deleteItem() {

        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String fullUrl = getDeleteUrl();
        httpRequest.setUrl(fullUrl);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_POST);
        httpRequest.setJSONPayload("{}");
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                String response = httpResponse.getResponseJSONString();
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    final String message = jsonObj.optString(AppConstants.MESSAGE);
                    final String status = jsonObj.optString(AppConstants.STATUS);
                    if (status.equals(AppConstants.SUCCESS)) {
                        showFailMessage(message);
                    }else {
                        Utils.showSnackBar(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        loaderHandler.loadData();

    }

    protected void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = null;
        if (mUserDetailsBase != null) {
            carImageArrayList = mUserDetailsBase.getImages();
            AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
            mViewPagerItemImages.setAdapter(adapter);

            indicatorImage = Utils.getCircleIndicator(getActivity(), carImageArrayList.size(), indicatorLayout);
            Utils.setIndicator(0, indicatorImage);
            if (carImageArrayList.size() <= 1) {
                indicatorLayout.setVisibility(View.INVISIBLE);
            } else {
                indicatorLayout.setVisibility(View.VISIBLE);
            }
            if(carImageArrayList.size() > 0){
                mShareImage = carImageArrayList.get(0).getPhoto_name();
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
                if(httpResponse.getValueObject() == null){
                    showFailMessage(getString(R.string.error_something_went_wrong));
                    return;
                }else if(((BaseVO)httpResponse.getValueObject()).getStatus().equals("fail")){
                    showFailMessage(((BaseVO) httpResponse.getValueObject()).getMessage());
                    return;
                }
                setDetails(httpResponse.getValueObject());
                getActivity().invalidateOptionsMenu();
            }
        });
        loaderHandler.loadData();
    }

    private void showFailMessage(String message){
        Utils.showSnackBar(getActivity(), message);
        mViewPagerItemImages.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getActivity() != null){
                    getActivity().finish();
                }
            }
        }, 2000);
    }


    abstract protected void openFullImageActivity();
    abstract protected String getCategory();
    abstract protected String getItemId();
    abstract protected String getDeleteUrl();
    abstract protected String getDetailsDownloadUrl();
    abstract protected void setDetails(ValueObject valueObject);
    abstract protected String getValueObjectFullyQualifiedName();

}
