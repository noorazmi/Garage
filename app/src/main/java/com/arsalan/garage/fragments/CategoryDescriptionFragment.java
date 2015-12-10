package com.arsalan.garage.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.ImageViewerActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.ItemDescriptionVo;
import com.nostra13.universalimageloader.core.ImageLoader;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

public class CategoryDescriptionFragment extends Fragment implements View.OnClickListener{

    private TextView mTextViewDescription;
    private TextView mTextViewPhone;
    private TextView mTextViewPhone1;
    private TextView mTextViewPhone2;
    private ImageView mImageViewItem;
    private String TAG = "CategoryDescriptionFragment";
    private ItemDescriptionVo mItemDescriptionVO;
    private String mDescriptionLanguage;

    public CategoryDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category_description, container, false);
        if(!TextUtils.isEmpty(mDescriptionLanguage) && mDescriptionLanguage.equals(AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH)){
            mTextViewDescription = (TextView) rootView.findViewById(R.id.textview_title_english);
        }else {
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
        if(Utils.isNetworkAvailable(getActivity())){
            performGET();
        }else {
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

    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.URL_ITEM_DESCRIPTION_BASE+getArguments().getString(AppConstants.ID)));
        httpRequest.setUrl(Urls.URL_ITEM_DESCRIPTION_BASE+getArguments().getString(AppConstants.ID));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(ItemDescriptionVo.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mItemDescriptionVO = (ItemDescriptionVo) httpResponse.getValueObject();
                setValuesInUI(mItemDescriptionVO);

                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }


    private void setValuesInUI(ItemDescriptionVo valueObject){
        if(valueObject == null){
            return;
        }
        mTextViewDescription.setText(valueObject.getResults().getDescription());


        //mTextViewPhone.setText(content);


        if(TextUtils.isEmpty(valueObject.getResults().getPhone())){
            mTextViewPhone.setVisibility(View.GONE);
        }else {
            mTextViewPhone.setVisibility(View.VISIBLE);
            mTextViewPhone.setText(getUnderlinedSpannable(valueObject.getResults().getPhone()));

        }
        if(TextUtils.isEmpty(valueObject.getResults().getPhone1())){
            mTextViewPhone1.setVisibility(View.GONE);
        }else {
            mTextViewPhone1.setVisibility(View.VISIBLE);
            mTextViewPhone1.setText(getUnderlinedSpannable(valueObject.getResults().getPhone1()));

        }

        if(TextUtils.isEmpty(valueObject.getResults().getPhone2())){
            mTextViewPhone2.setVisibility(View.GONE);
        }else {
            mTextViewPhone2.setVisibility(View.VISIBLE);
            mTextViewPhone2.setText(getUnderlinedSpannable(valueObject.getResults().getPhone2()));

        }
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!TextUtils.isEmpty(valueObject.getResults().getImage())){
            imageLoader.displayImage(valueObject.getResults().getImage(), mImageViewItem);
        }

    }

    private SpannableString getUnderlinedSpannable(String str){
        SpannableString content = new SpannableString(str);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }


    private void showZoomedImage() {

        if(TextUtils.isEmpty(AppConstants.EXTRA_IMAGE_PATH)){
            return;
        }
        Intent imageViewerIntent = new Intent(getActivity(), ImageViewerActivity.class);
        imageViewerIntent.putExtra(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
        imageViewerIntent.putExtra(AppConstants.EXTRA_IMAGE_PATH, mItemDescriptionVO.getResults().getImage());
        startActivity(imageViewerIntent);
    }


    private void initCall(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_phone_number:
                initCall(mItemDescriptionVO.getResults().getPhone());
            break;
            case R.id.textview_phone_number1:
                initCall(mItemDescriptionVO.getResults().getPhone1());
                break;
            case R.id.textview_phone_number2:
                initCall(mItemDescriptionVO.getResults().getPhone2());
                break;

            case R.id.imageview_icon:
                showZoomedImage();
                break;
        }
    }
}
