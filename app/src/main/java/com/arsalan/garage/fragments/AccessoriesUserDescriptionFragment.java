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

import com.arsalan.garage.R;
import com.arsalan.garage.activities.EditPostActivity;
import com.arsalan.garage.activities.FullImageActivity;
import com.arsalan.garage.adapters.AlwakalatAgencyDescriptionCarsViewPagerAdapter;
import com.arsalan.garage.adapters.ShareListAdapter;
import com.arsalan.garage.models.AccessoriesUserDetailsData;
import com.arsalan.garage.models.ImageInfo;
import com.arsalan.garage.models.ShareOptionItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.CustomDialogHelper;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

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
public class AccessoriesUserDescriptionFragment extends Fragment {

    private ViewPager mViewPagerCarImages;
    private String TAG = "AccessoriesUserDescriptionFra";
    private AccessoriesUserDetailsData mAccessoriesUserDetailsData;
    private GestureDetector mGestureDetector;
    private TextView mTextviewDescription;
    private TextView mTextviewPhone;
    private AlertDialog mShareOptionsAlertDialog;
    private String mShareImage;
    private String mShareText;

    public AccessoriesUserDescriptionFragment() {
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
        mTextviewDescription = (TextView) rootView.findViewById(R.id.textview_description);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);
        if(mAccessoriesUserDetailsData != null && mAccessoriesUserDetailsData.getResults().getIs_owner() == 1){
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
                AccessoriesUserDetailsData.AccessoriesUserDetails accessoriesUserDetails = mAccessoriesUserDetailsData.getResults();
                intent.putExtra(AppConstants.ID, accessoriesUserDetails.getAccessories_id());
                intent.putExtra(AppConstants.MODEL, accessoriesUserDetails.getModel());
                intent.putExtra(AppConstants.TITLE, accessoriesUserDetails.getTitle());
                intent.putExtra(AppConstants.PHONE, accessoriesUserDetails.getPhone());
                intent.putExtra(AppConstants.PRICE, accessoriesUserDetails.getPrice());
                intent.putExtra(AppConstants.DESCRIPTION, accessoriesUserDetails.getDescription());
                intent.putExtra(AppConstants.CATEGORY, AppConstants.ACCESSORIES);
                intent.putExtra(AppConstants.SUB_CATEGORY, accessoriesUserDetails.getMake_region_name());

                ArrayList<ImageInfo> carImageArrayList = mAccessoriesUserDetailsData.getResults().getImages();
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

    private void deleteItem(){

        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        String carId = getArguments().getString(AppConstants.ID);
        String fullUrl = Urls.ACCESSORIES_DELETE + "?device_phone="+ PrefUtility.getAccessToken() +"&delete_id="+ carId;
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



    private void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = null;
        if(mAccessoriesUserDetailsData != null){
            carImageArrayList = mAccessoriesUserDetailsData.getResults().getImages();
            mShareImage = carImageArrayList.get(0).getPhoto_name();
            AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
            mViewPagerCarImages.setAdapter(adapter);
        }

    }


    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        //String baseUrl = getArguments().getString(AppConstants.URL)+"/";
        String carId = getArguments().getString(AppConstants.ID);
        String fullUrl = Urls.ACCESSORIESUSERDETAIL_BASE + carId + "/" + PrefUtility.getAccessToken();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + fullUrl);
        httpRequest.setUrl(fullUrl);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(AccessoriesUserDetailsData.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mAccessoriesUserDetailsData = (AccessoriesUserDetailsData) httpResponse.getValueObject();
                getActivity().invalidateOptionsMenu();
                setPagerAdapter();
                setDescription();
            }
        });
        loaderHandler.loadData();
    }

    private void setDescription(){

        if(mAccessoriesUserDetailsData == null || mAccessoriesUserDetailsData.getResults() == null){
           return;
        }
        mShareText = mAccessoriesUserDetailsData.getResults().getDescription();
        mTextviewDescription.setText(mShareText);
        mTextviewPhone.setText(mAccessoriesUserDetailsData.getResults().getPhone());
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
        intent.putExtra(AppConstants.EXTRA_IMAGE_URL, Urls.ACCESSORIES_USER_DETAILS+ getArguments().getString(AppConstants.ID));
        //intent.putExtra(AppConstants.EXTRA_GALLERY_FOR, AppConstants.EXTRA_GALLERY_FOR_ACCESSORIES_USER);
        intent.putExtra(AppConstants.EXTRA_INDEX, mViewPagerCarImages.getCurrentItem());
        startActivity(intent);
    }
}
