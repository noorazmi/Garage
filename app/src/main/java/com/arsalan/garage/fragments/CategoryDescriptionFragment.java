package com.arsalan.garage.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.ImageViewerActivity;
import com.arsalan.garage.adapters.ShareListAdapter;
import com.arsalan.garage.models.ShareOptionItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.CustomDialogHelper;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.ItemDescriptionVO;
import com.bumptech.glide.Glide;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

public class CategoryDescriptionFragment extends Fragment implements View.OnClickListener {

    private TextView mTextViewDescription;
    private TextView mTextViewPhone;
    private TextView mTextViewPhone1;
    private TextView mTextViewPhone2;
    private ImageView mImageViewItem;
    private String TAG = "CategoryDescriptionFragment";
    private ItemDescriptionVO mItemDescriptionVO;
    private String mDescriptionLanguage;
    protected AlertDialog mShareOptionsAlertDialog;
    private String mShareImage;
    private String mShareText;
    private String mPhoneNumbers = "";

    public CategoryDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category_description, container, false);
        if (!TextUtils.isEmpty(mDescriptionLanguage) && mDescriptionLanguage.equals(AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH)) {
            mTextViewDescription = (TextView) rootView.findViewById(R.id.textview_title_english);
        } else {
            mTextViewDescription = (TextView) rootView.findViewById(R.id.textview_title);
        }
        mTextViewDescription.setVisibility(View.VISIBLE);
        mTextViewPhone = (TextView) rootView.findViewById(R.id.textview_phone_number);
        mTextViewPhone1 = (TextView) rootView.findViewById(R.id.textview_phone_number1);
        mTextViewPhone2 = (TextView) rootView.findViewById(R.id.textview_phone_number2);
        mImageViewItem = (ImageView) rootView.findViewById(R.id.imageview_icon);
        mTextViewPhone.setOnClickListener(this);
        mTextViewPhone1.setOnClickListener(this);
        mTextViewPhone2.setOnClickListener(this);
        mImageViewItem.setOnClickListener(this);
        rootView.findViewById(R.id.imageview_email).setOnClickListener(this);
        rootView.findViewById(R.id.imageview_sms).setOnClickListener(this);
        if (Utils.isNetworkAvailable(getActivity())) {
            performGET();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDescriptionLanguage = getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void performGET() {
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.URL_ITEM_DESCRIPTION_BASE + getArguments().getString(AppConstants.ID)));
        httpRequest.setUrl(Urls.URL_ITEM_DESCRIPTION_BASE + getArguments().getString(AppConstants.ID));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ItemDescriptionVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mItemDescriptionVO = (ItemDescriptionVO) httpResponse.getValueObject();
                setValuesInUI(mItemDescriptionVO);

                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }


    private void setValuesInUI(ItemDescriptionVO valueObject) {
        if (valueObject == null) {
            return;
        }
        mTextViewDescription.setText(valueObject.getResults().getDescription());
        mShareText = valueObject.getResults().getDescription();

        if (TextUtils.isEmpty(valueObject.getResults().getPhone())) {
            mTextViewPhone.setVisibility(View.GONE);
        } else {
            mTextViewPhone.setVisibility(View.VISIBLE);
            mTextViewPhone.setText(getUnderlinedSpannable(valueObject.getResults().getPhone()));
            mShareText = mShareText + "\n" + getUnderlinedSpannable(valueObject.getResults().getPhone());
            mPhoneNumbers = mPhoneNumbers + "\n" + valueObject.getResults().getPhone();

        }
        if (TextUtils.isEmpty(valueObject.getResults().getPhone1())) {
            mTextViewPhone1.setVisibility(View.GONE);
        } else {
            mTextViewPhone1.setVisibility(View.VISIBLE);
            mTextViewPhone1.setText(getUnderlinedSpannable(valueObject.getResults().getPhone1()));
            mShareText = mShareText + "\n" + getUnderlinedSpannable(valueObject.getResults().getPhone1());
            mPhoneNumbers = mPhoneNumbers + "\n" + valueObject.getResults().getPhone1();
        }

        if (TextUtils.isEmpty(valueObject.getResults().getPhone2())) {
            mTextViewPhone2.setVisibility(View.GONE);
        } else {
            mTextViewPhone2.setVisibility(View.VISIBLE);
            mTextViewPhone2.setText(getUnderlinedSpannable(valueObject.getResults().getPhone2()));
            mShareText = mShareText + "\n" + getUnderlinedSpannable(valueObject.getResults().getPhone2());
            mPhoneNumbers = mPhoneNumbers + valueObject.getResults().getPhone2() + ";";
        }
        String imageUrl = null;
        if (!TextUtils.isEmpty(valueObject.getResults().getImage())) {
            //imageLoader.displayImage(valueObject.getResults().getImage(), mImageViewItem);
            imageUrl = valueObject.getResults().getImage();
        } else {
            imageUrl = getArguments().getString(AppConstants.IMAGE_URL);
        }

        Glide.with(getActivity())
                .load(imageUrl).placeholder(R.mipmap.ic_launcher)
                //.override(500, 304)
                .into(mImageViewItem);
        mShareImage = imageUrl;
    }

    private SpannableString getUnderlinedSpannable(String str) {
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }


    private void showZoomedImage() {

        if (TextUtils.isEmpty(AppConstants.EXTRA_IMAGE_PATH)) {
            return;
        }
        Intent imageViewerIntent = new Intent(getActivity(), ImageViewerActivity.class);
        imageViewerIntent.putExtra(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
        imageViewerIntent.putExtra(AppConstants.EXTRA_IMAGE_PATH, mItemDescriptionVO.getResults().getImage());
        startActivity(imageViewerIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_phone_number:
                Utils.initCall(mItemDescriptionVO.getResults().getPhone(), getActivity());
                break;
            case R.id.textview_phone_number1:
                Utils.initCall(mItemDescriptionVO.getResults().getPhone1(), getActivity());
                break;
            case R.id.textview_phone_number2:
                Utils.initCall(mItemDescriptionVO.getResults().getPhone2(), getActivity());
                break;
            case R.id.imageview_email:
                ShareUtil.openGmail(getActivity(), AppConstants.WRONG_PHONE_REPORT_EMAIL_ADDRESS,getString(R.string.phone_numbers_not_working), getString(R.string.following_phone_numbers_not_working)+ mPhoneNumbers);
                break;
            case R.id.imageview_sms:

                ShareUtil.sendSMS(getActivity(), AppConstants.WRONG_PHONE_REPORT_SMS_NUMBER, getString(R.string.following_phone_numbers_not_working)+ mPhoneNumbers);
                break;
            case R.id.imageview_icon:
                showZoomedImage();
                break;
        }
    }
}
