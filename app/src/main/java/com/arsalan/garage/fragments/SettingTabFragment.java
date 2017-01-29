package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingTabFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SettingTabFragment";
    protected String mShareImage = "http://ronsgaragea2.com/wp-content/uploads/ronsGarageAnnArborAutoRepair_slide21.jpg";
    protected String mShareText = "Hi there, Check this app https://play.google.com/store/apps/details?id=com.famelive";
    private ImageView mImageViewTwitter;
    private ImageView mImageViewFacebok;
    private ImageView mImageViewWhatApp;
    private TextView mTextViewTwitter;
    private TextView mTextViewFacebook;
    private TextView mTextViewWhatsApp;

    private TextView mTextViewPhone1;
    private TextView mTextViewPhone2;
    private TextView mTextViewPhone3;


    public SettingTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting_tab, container, false);
        mImageViewTwitter = (ImageView) rootView.findViewById(R.id.imageview_twitter);
        mImageViewFacebok = (ImageView) rootView.findViewById(R.id.imageview_facebook);
        mImageViewWhatApp = (ImageView) rootView.findViewById(R.id.imageview_whatsapp);
        mTextViewTwitter = (TextView) rootView.findViewById(R.id.textview_twitter);
        mTextViewFacebook = (TextView) rootView.findViewById(R.id.textview_facebook);
        mTextViewWhatsApp = (TextView) rootView.findViewById(R.id.textview_whatsapp);
        mTextViewPhone1 = (TextView) rootView.findViewById(R.id.textview_phone1);
        mTextViewPhone2 = (TextView) rootView.findViewById(R.id.textview_phone2);
        mTextViewPhone3 = (TextView) rootView.findViewById(R.id.textview_phone3);

        rootView.findViewById(R.id.whatsapp1).setOnClickListener(this);
        rootView.findViewById(R.id.whatsapp2).setOnClickListener(this);
        rootView.findViewById(R.id.whatsapp2).setOnClickListener(this);


        mImageViewTwitter.setOnClickListener(this);
        mImageViewFacebok.setOnClickListener(this);
        mImageViewWhatApp.setOnClickListener(this);
        mTextViewTwitter.setOnClickListener(this);
        mTextViewFacebook.setOnClickListener(this);
        mTextViewWhatsApp.setOnClickListener(this);
        mTextViewPhone1.setOnClickListener(this);
        mTextViewPhone2.setOnClickListener(this);
        mTextViewPhone3.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.textview_phone1:
                Utils.initCall(mTextViewPhone1.getText().toString(), getActivity());
                break;
            case R.id.textview_phone2:
                Utils.initCall(mTextViewPhone2.getText().toString(), getActivity());
                break;
            case R.id.textview_phone3:
                Utils.initCall(mTextViewPhone3.getText().toString(), getActivity());
                break;
            case R.id.imageview_twitter:
                ShareUtil.shareOnTwitter(getActivity(), mShareText, mShareImage);
                break;
            case R.id.imageview_facebook:
                ShareUtil.shareOnFacebook(getActivity(), mShareText, mShareImage);
                break;
            case R.id.imageview_whatsapp:
                ShareUtil.shareOnWhatsApp(getActivity(), mShareText, mShareImage);
                break;
            case R.id.textview_twitter:
                ShareUtil.shareOnTwitter(getActivity(), mShareText, mShareImage);
                break;
            case R.id.textview_facebook:
                ShareUtil.shareOnFacebook(getActivity(), mShareText, mShareImage);
                break;
            case R.id.textview_whatsapp:
                ShareUtil.shareOnWhatsApp(getActivity(), mShareText, mShareImage);
                break;
            case R.id.whatsapp1:
                shareOnWhatApp();
                break;
            case R.id.whatsapp2:
                shareOnWhatApp();
                break;
            case R.id.whatsapp3:
                shareOnWhatApp();
                break;
            default:
                break;
        }
    }

    private void shareOnWhatApp(){
        ShareUtil.shareOnWhatsApp(getActivity(), "", null);
    }
}
