package com.arsalan.garage.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.HomeActivity;
import com.arsalan.garage.uicomponents.CustomProgressDialog;
import com.arsalan.garage.utils.LocaleHelper;
import com.arsalan.garage.utils.LocaleUtils;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Utils;

import java.util.Locale;

import static com.arsalan.garage.utils.ShareUtil.getWhatsAppTextShareIntent;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingTabFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SettingTabFragment";
    protected String mShareImage = "http://ronsgaragea2.com/wp-content/uploads/ronsGarageAnnArborAutoRepair_slide21.jpg";
    protected String mShareText = "Hi there, Check this app https://play.google.com/store/apps/details?id=com.famelive";
    //TODO Remove the place holder text and image in share functionality above
    private CustomProgressDialog progressDialog;
    private TextView mTextViewLanguage;

    public SettingTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting_tab, container, false);

        rootView.findViewById(R.id.whatsapp1).setOnClickListener(this);
        rootView.findViewById(R.id.whatsapp2).setOnClickListener(this);
        rootView.findViewById(R.id.whatsapp3).setOnClickListener(this);
        rootView.findViewById(R.id.share).setOnClickListener(this);
        rootView.findViewById(R.id.logout).setOnClickListener(this);
        rootView.findViewById(R.id.gmail).setOnClickListener(this);
        rootView.findViewById(R.id.forgot_password).setOnClickListener(this);
        mTextViewLanguage = (TextView) rootView.findViewById(R.id.language);
        mTextViewLanguage.setOnClickListener(this);
        setLanguageChangeText();

        return rootView;
    }

    private void setLanguageChangeText(){
        //String locale = LocaleHelper.getPersistedData(getActivity(), "ar");
        String locale = LocaleUtils.getPersistedData(getActivity(), "ar");
        if(locale.equals("ar")){
            mTextViewLanguage.setText(getString(R.string.english));
        }else {
            mTextViewLanguage.setText(getString(R.string.arabic_ar));
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            /*case R.id.imageview_twitter:
                ShareUtil.shareOnTwitter(getActivity(), mShareText, mShareImage);
                break;
            case R.id.imageview_facebook:
                ShareUtil.shareOnFacebook(getActivity(), mShareText, mShareImage);
                break;
            case R.id.imageview_whatsapp:
                ShareUtil.shareOnWhatsApp(getActivity(), mShareText, mShareImage);
                break;*/
            case R.id.whatsapp1:
                shareOnWhatApp();
                break;
            case R.id.whatsapp2:
                shareOnWhatApp();
                break;
            case R.id.whatsapp3:
                shareOnWhatApp();
                break;
            case R.id.share:
                ShareUtil.shareImageWithText(getActivity(), mShareText, mShareImage);
                break;
            case R.id.logout:
                handleLogout();
                break;
            case R.id.gmail:
                handleGmail(((TextView) v).getText().toString());
                break;
            case R.id.forgot_password:
                Utils.createForgotPasswordDialog(getActivity());
                break;
            case R.id.language:
                changeLanguage();
                break;
            default:
                break;
        }
    }

    private void changeLanguage() {
        if (mTextViewLanguage.getText().toString().equalsIgnoreCase("English")) {
            //LocaleHelper.setLocale(getActivity(), "en");
            LocaleUtils.setLocale(new Locale("en"));
            LocaleUtils.persist(getActivity(), "en");

        } else {
            //LocaleHelper.setLocale(getActivity(), "ar");
            LocaleUtils.setLocale(new Locale("ar"));
            LocaleUtils.persist(getActivity(), "ar");
        }
        LocaleUtils.updateConfig(GarageApp.getInstance(), getActivity().getResources().getConfiguration());
        //Restart the activity again to see the changes immediately
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void handleGmail(String recipient) {
        ShareUtil.openGmail(getActivity(), recipient);
    }

    private void handleLogout() {
        if (TextUtils.isEmpty(PrefUtility.getAccessToken())) {
            Utils.showSnackBar(getActivity(), getString(R.string.not_logged_in));
        } else {
            performLogout();
        }
    }

    private void shareOnWhatApp() {
        ShareUtil.shareUsingTargetApp(getActivity(), getWhatsAppTextShareIntent(" "), ShareUtil.WHATSAPP);
    }

    private void performLogout() {

        progressDialog = new CustomProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Utils.showSnackBar(getActivity(), getString(R.string.successfully_logge_out));
                PrefUtility.deleteUserLoginToken();
            }
        }, 2000);
    }
}
