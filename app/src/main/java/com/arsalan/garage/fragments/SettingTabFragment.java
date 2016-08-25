package com.arsalan.garage.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.LoginActivity;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingTabFragment extends Fragment {

    private static final String TAG = "SettingTabFragment";
    private Button mButtonLogin;

    public SettingTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting_tab, container, false);
        mButtonLogin = (Button) rootView.findViewById(R.id.button_login);
        if(PrefUtility.isLoggedIn()){
            mButtonLogin.setText(R.string.logout);
        }else {
            mButtonLogin.setText(R.string.login);
        }
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonLogin.getText().toString().equals("Login")){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 123);
                }else {
                    Utils.showToastMessage(getActivity(), "Logout Successfully");
                    PrefUtility.clearUsrPrefs();
                    mButtonLogin.setText(R.string.login);
                }

            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            mButtonLogin.setText(R.string.logout);
        }


    }
}
