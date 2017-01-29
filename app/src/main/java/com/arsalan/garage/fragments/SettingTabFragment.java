package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.uicomponents.CustomProgressDialog;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.ShareUtil;
import com.arsalan.garage.utils.Utils;

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
        rootView.findViewById(R.id.language).setOnClickListener(this);

        return rootView;
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
                handleGmail(((TextView)v).getText().toString());
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
        String appLanguage = PrefUtility.getCurrentLanguage();
        switch (appLanguage){
            case AppConstants.APP_LANGUAGE_AR:
                break;
            case AppConstants.APP_LANGUAGE_EN:
                break;
        }
    }

    private void handleGmail(String recipient) {
        ShareUtil.openGmail(getActivity(), recipient);
    }

    private void handleLogout(){
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
