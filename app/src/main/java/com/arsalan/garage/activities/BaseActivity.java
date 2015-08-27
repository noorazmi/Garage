package com.arsalan.garage.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.arsalan.garage.R;


/**
 * <p/>
 * Project: <b>Garage</b><br/>
 * Created by: Noor  Alam on 22/06/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public abstract class BaseActivity extends AppCompatActivity {


        TextView mTextViewTitle;
        Activity mActivity = this;
        private static final String TAG = "BaseActionBarActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // ApptimizeUtils apptimizeUtils = new ApptimizeUtils(this);
        }

        public void setToolbar(Toolbar toolbar, boolean displayHomeAsUpEnabled) {
            setSupportActionBar(toolbar);

            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            int padding = 0;
            if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
                padding = getResources().getDimensionPixelOffset(R.dimen.toolbar_padding);
            } else {
                padding = 0;
            }
            toolbar.setPadding(0, padding, 0, 0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
            mTextViewTitle = (TextView) toolbar.findViewById(R.id.textview_toolbar_title);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        public void setToolbar(Toolbar toolbar, CharSequence title, boolean displayHomeAsUpEnabled) {
            setToolbar(toolbar, displayHomeAsUpEnabled);
            setTitle(title);
        }

        public void setToolbar(Toolbar toolbar, int title, boolean displayHomeAsUpEnabled) {
            setToolbar(toolbar, displayHomeAsUpEnabled);
            setTitle(title);
        }

        public void setCustomToolbar(Toolbar toolbar, int title, boolean displayHomeAsUpEnabled) {
            setSupportActionBar(toolbar);
            toolbar.setPadding(0, 0, 0, 0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
            mTextViewTitle = (TextView) toolbar.findViewById(R.id.textview_toolbar_title);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            setTitle(title);
        }

        public void setBackIconIndicator(int resourceId) {
            getSupportActionBar().setHomeAsUpIndicator(resourceId);
        }

        @Override
        public void setTitle(int resId) {
            super.setTitle(resId);
            mTextViewTitle.setText(resId);
        }

        @Override
        public void setTitle(CharSequence title) {
            super.setTitle(title);
            mTextViewTitle.setText(title);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }


