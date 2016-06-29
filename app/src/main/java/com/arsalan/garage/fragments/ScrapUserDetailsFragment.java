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
import android.widget.ListView;
import android.widget.TextView;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.EditPostActivity;
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
import com.arsalan.garage.vo.ScrapUserDetailsData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ScrapUserDetailsFragment extends Fragment {

    private ViewPager mViewPagerCarImages;
    private String TAG = "ScrapUserDetailsFragment";
    private ScrapUserDetailsData mScrapUserDetailsData;
    private GestureDetector mGestureDetector;
    private int mCurrentCarIndex = 0;
    private TextView mTextviewDescription;
    private TextView mTextviewPhone;
    private AlertDialog mShareOptionsAlertDialog;

    public ScrapUserDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_marine_user_description, container, false);
        mViewPagerCarImages = (ViewPager) rootView.findViewById(R.id.viewpager_car_images);
        mTextviewDescription = (TextView) rootView.findViewById(R.id.textview_type);
        mTextviewPhone = (TextView) rootView.findViewById(R.id.textview_phone);
        mGestureDetector = new GestureDetector(getActivity(), mSimpleOnGestureListener);
        mViewPagerCarImages.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });
        if(Utils.isNetworkAvailable(getActivity())){
            performGET();
        }else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    private void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = null;
        if(mScrapUserDetailsData != null){
            carImageArrayList = mScrapUserDetailsData.getResults().getImages();
            AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
            mViewPagerCarImages.setAdapter(adapter);
        }

    }


    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //String baseUrl = getArguments().getString(AppConstants.URL)+"/";
        String carId = getArguments().getString(AppConstants.ID);
        String fullUrl = Urls.SCRAPUSERDETAIL_BASE + carId + GarageApp.DEVICE_UUID_WITH_SLASH;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + fullUrl);
        httpRequest.setUrl(fullUrl);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ScrapUserDetailsData.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mScrapUserDetailsData = (ScrapUserDetailsData) httpResponse.getValueObject();
                getActivity().invalidateOptionsMenu();
                setPagerAdapter();
                setDescription();
            }
        });
        loaderHandler.loadData();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        if(mScrapUserDetailsData != null && mScrapUserDetailsData.getResults().getIs_owner() == 1){
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
                ScrapUserDetailsData.ScrapUserDetails scrapUserDetails = mScrapUserDetailsData.getResults();
                intent.putExtra(AppConstants.ID, scrapUserDetails.getScrap_id());
                intent.putExtra(AppConstants.MODEL, scrapUserDetails.getModel());
                intent.putExtra(AppConstants.TITLE, scrapUserDetails.getTitle());
                intent.putExtra(AppConstants.PHONE, scrapUserDetails.getPhone());
                intent.putExtra(AppConstants.PRICE, scrapUserDetails.getPrice());
                intent.putExtra(AppConstants.DESCRIPTION, scrapUserDetails.getDescription());
                intent.putExtra(AppConstants.CATEGORY, AppConstants.SCRAP);
                intent.putExtra(AppConstants.SUB_CATEGORY, scrapUserDetails.getMake_region_name());

                ArrayList<ImageInfo> carImageArrayList = mScrapUserDetailsData.getResults().getImages();
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


    private void deleteItem(){

        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String carId = getArguments().getString(AppConstants.ID);
        String fullUrl = Urls.SCRAP_DELETE + "?device_phone="+GarageApp.DEVICE_UUID +"&delete_id="+ carId;
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + fullUrl);
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
                    Utils.showSnackBar(getActivity(), message);
                    if(status.equals(AppConstants.SUCCESS)){
                        mViewPagerCarImages.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getActivity().finish();
                            }
                        }, 2500);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        loaderHandler.loadData();

    }

    private void showShareOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ListView mBankListView = new ListView(getActivity());

        final ShareListAdapter shareListAdapter = new ShareListAdapter(getActivity(), Utils.getShareOptions());
        mBankListView.setAdapter(shareListAdapter);
        mBankListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ShareOptionItem shareOptionItem  = (ShareOptionItem) shareListAdapter.getItem(position);
                mShareOptionsAlertDialog.dismiss();
                switch (position){
                    case 0://facebook
                        ShareUtil.shareOnFacebook(getActivity(), "Sharing View whatapp", "http://cloud.americanswan.com/image/upload/if_pg_gt_1,q_60/if_else,q_80/f_auto,fl_lossy,w_380,h_507/PROD/IMG/PRODUCT/161ASMTFSHBG15-BKWH%20(1)");
                        break;
                    case 1://twitter
                        ShareUtil.shareOnTwitter(getActivity(), "Sharing View whatapp", "http://cloud.americanswan.com/image/upload/if_pg_gt_1,q_60/if_else,q_80/f_auto,fl_lossy,w_380,h_507/PROD/IMG/PRODUCT/161ASMTFSHBG15-BKWH%20(1)");
                        break;
                    case 2://whatsapp
                        ShareUtil.shareOnWhatsApp(getActivity(), "Sharing View whatapp", "http://cloud.americanswan.com/image/upload/if_pg_gt_1,q_60/if_else,q_80/f_auto,fl_lossy,w_380,h_507/PROD/IMG/PRODUCT/161ASMTFSHBG15-BKWH%20(1)");
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

    private void setDescription(){
        mTextviewDescription.setText(mScrapUserDetailsData.getResults().getDescription());
        mTextviewPhone.setText(mScrapUserDetailsData.getResults().getPhone());
        mTextviewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.initCall(mTextviewPhone.getText().toString(), getActivity());
            }
        });
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
        //intent.putExtra(AppConstants.EXTRA_IMAGE_URL, Urls.SHOWROOM_CAR+getArguments().getString(AppConstants.EXTRA_CAR_ID));
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, Urls.SCRAPUSERDETAIL_BASE + getArguments().getString(AppConstants.ID));
        intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_SCRAP_USER);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerCarImages.getCurrentItem());
        startActivity(intent);
    }
}
