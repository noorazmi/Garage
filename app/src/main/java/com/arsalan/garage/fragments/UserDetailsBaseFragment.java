package com.arsalan.garage.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ListView;
import android.widget.TextView;

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
 * Created by: Noor  Alam on 14/07/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public abstract class UserDetailsBaseFragment extends Fragment{

    protected ViewPager mViewPagerCarImages;
    protected GestureDetector mGestureDetector;
    protected int mCurrentCarIndex = 0;
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
        mTextViewModel = (TextView) rootView.findViewById(R.id.textview_model);
        mTextViewPrice = (TextView) rootView.findViewById(R.id.textview_price);
        mTextViewTitle = (TextView) rootView.findViewById(R.id.textview_title);
        mImageViewEmail = (ImageView) rootView.findViewById(R.id.imageview_email);
        mGestureDetector = new GestureDetector(getActivity(), mSimpleOnGestureListener);
        mViewPagerCarImages.setOnTouchListener(new View.OnTouchListener() {
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
                ShareUtil.shareOnGmail(getActivity(), mShareText, userDetailsBase.getImages().get(0).getPhoto_name());
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
                intent.putExtra(AppConstants.MODEL, mUserDetailsBase.getModel());
                intent.putExtra(AppConstants.TITLE, mUserDetailsBase.getTitle());
                intent.putExtra(AppConstants.PHONE, mUserDetailsBase.getPhone());
                intent.putExtra(AppConstants.PRICE, mUserDetailsBase.getPrice());
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
                    Utils.showSnackBar(getActivity(), message);
                    if (status.equals(AppConstants.SUCCESS)) {
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

    protected void setPagerAdapter() {
        ArrayList<ImageInfo> carImageArrayList = null;
        if (mUserDetailsBase != null) {
            carImageArrayList = mUserDetailsBase.getImages();
            mShareImage = carImageArrayList.get(0).getPhoto_name();
            AlwakalatAgencyDescriptionCarsViewPagerAdapter adapter = new AlwakalatAgencyDescriptionCarsViewPagerAdapter(getFragmentManager(), carImageArrayList);
            mViewPagerCarImages.setAdapter(adapter);
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
                setDetails(httpResponse.getValueObject());
                getActivity().invalidateOptionsMenu();
            }
        });
        loaderHandler.loadData();
    }


    abstract protected void openFullImageActivity();
    abstract protected String getCategory();
    abstract protected String getItemId();
    abstract protected String getDeleteUrl();
    abstract protected String getDetailsDownloadUrl();
    abstract protected void setDetails(ValueObject valueObject);
    abstract protected String getValueObjectFullyQualifiedName();

}
