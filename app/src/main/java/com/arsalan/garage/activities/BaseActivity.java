package com.arsalan.garage.activities;

import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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


        //private TextView mTextViewTitle;
        private static final String TAG = "BaseActionBarActivity";

        public void setToolbar(Toolbar toolbar, boolean displayHomeAsUpEnabled) {
            setSupportActionBar(toolbar);

            int currentapiVersion = Build.VERSION.SDK_INT;
            int padding = 0;
            if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
                padding = getResources().getDimensionPixelOffset(R.dimen.toolbar_padding);
            } else {
                padding = 0;
            }
            toolbar.setPadding(0, padding, 0, 0);
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
            //mTextViewTitle = (TextView) toolbar.findViewById(R.id.textview_toolbar_title);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        public void setToolbar(Toolbar toolbar, CharSequence title, boolean displayHomeAsUpEnabled) {
            setToolbar(toolbar, displayHomeAsUpEnabled);
            setTitle(title);
        }

    public void setToolbar(Toolbar toolbar, String title, boolean displayHomeAsUpEnabled, int gravity) {
        setToolbar(toolbar, displayHomeAsUpEnabled);
        setCustomTitle(title, toolbar ,gravity);
    }

    public void setCustomTitle(String title, Toolbar toolbar, int gravity){
        TextView textViewTitle = (TextView) getLayoutInflater().inflate(R.layout.textview_toolbar_title, null);
        textViewTitle.setText(title);
        toolbar.addView(textViewTitle, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        ));

    }

    public void setCustomTitleEnglish(String title, Toolbar toolbar){
        setToolbar(toolbar, true);
        TextView textViewTitle = (TextView) getLayoutInflater().inflate(R.layout.textview_toolbar_title_english, null);
        textViewTitle.setText(title);
        toolbar.addView(textViewTitle, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
        ));

    }

        public void setToolbar(Toolbar toolbar, int title, boolean displayHomeAsUpEnabled) {
            setToolbar(toolbar, displayHomeAsUpEnabled);
            setTitle(title);
        }


        public void setBackIconIndicator(int resourceId) {
            getSupportActionBar().setHomeAsUpIndicator(resourceId);
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


