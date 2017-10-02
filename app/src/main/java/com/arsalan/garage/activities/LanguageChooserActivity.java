package com.arsalan.garage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.LocaleHelper;
import com.arsalan.garage.utils.LocaleUtils;

import java.util.Locale;

public class LanguageChooserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_chooser);
    }

    public void onArabicClick(View view){
        //LocaleHelper.setLocale(this, "ar");
        LocaleUtils.setLocale(new Locale("ar"));
        LocaleUtils.persist(this, "ar");
        startHomeActivity();
    }

    public void onEnglishClick(View view){
        //LocaleHelper.setLocale(this, "en");
        LocaleUtils.setLocale(new Locale("en"));
        LocaleUtils.persist(this, "en");
        startHomeActivity();
    }

    private void startHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
