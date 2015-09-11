package com.arsalan.garage.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
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
import com.arsalan.garage.vo.ItemDescriptionVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryDescriptionActivityFragment extends Fragment implements View.OnClickListener{

    private TextView mTextViewDescription;
    private TextView mTextViewPhone;
    private ImageView mImageViewItem;
    private String TAG = "CategoryDescriptionActivityFragment";
    private ItemDescriptionVO mItemDescriptionVO;

    public CategoryDescriptionActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category_description, container, false);
        mTextViewDescription = (TextView) rootView.findViewById(R.id.textview_title);
        mTextViewPhone = (TextView) rootView.findViewById(R.id.textview_phone_number);
        mImageViewItem = (ImageView) rootView.findViewById(R.id.imageview_icon);
        mTextViewPhone.setOnClickListener(this);
        mImageViewItem.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        performGET();
    }

    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(false);
        Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + (Urls.URL_ITEM_DESCRIPTION_BASE+getArguments().getString(AppConstants.ID)));
        httpRequest.setUrl(Urls.URL_ITEM_DESCRIPTION_BASE+getArguments().getString(AppConstants.ID));
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


    private void setValuesInUI(ItemDescriptionVO valueObject){
        mTextViewDescription.setText(valueObject.getResults().getDescription());

        SpannableString content = new SpannableString(valueObject.getResults().getPhone());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        mTextViewPhone.setText(content);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(valueObject.getResults().getImage(), mImageViewItem);
    }



    private void showZoomedImage() {

        Intent imageViewerIntent = new Intent(getActivity(), ImageViewerActivity.class);
        imageViewerIntent.putExtra(AppConstants.EXTRA_TITLE, "Title");
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
            case R.id.imageview_icon:
                showZoomedImage();
                break;
        }
    }
}
