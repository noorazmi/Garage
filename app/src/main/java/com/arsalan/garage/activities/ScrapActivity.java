package com.arsalan.garage.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.HelpCarActivityFragment;
import com.arsalan.garage.fragments.LevelOneMenuActivityFragment;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;

public class ScrapActivity extends BaseActivity implements LevelOneMenuActivityFragment.OnMenuItemClickListener{

    private FragmentManager mFragmentManager;
    private int scrapeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getBundleExtra(AppConstants.BUNDLE_EXTRA);
        scrapeType =  bundle.getInt(AppConstants.SCRAP_TYPE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar, getToolbarTitle(), true, Gravity.CENTER);



        //setTitle();

        setMenuHolderFragment();
    }

    private String getToolbarTitle(){
        String title = null;

        if(scrapeType == AppConstants.SCRAP_MAIN){
//            getSupportActionBar().setTitle("السكراب");
            title = "السكراب";
        }else if(scrapeType == AppConstants.SCRAP_AMERICA){
//            getSupportActionBar().setTitle("Scrap-امريكي");
            //title = "Scrap-امريكي";
            title = "Scrap";
        }else if(scrapeType == AppConstants.SCRAP_AWARBY){
            //getSupportActionBar().setTitle("Scrap-اوربي");
            //title =  "Scrap-اوربي";
            title =  "Scrap";
        }else if(scrapeType == AppConstants.SCRAP_ASIBI){
            //getSupportActionBar().setTitle("Scrap-اسيوي");
//            title = "Scrap-اسيوي";
            title = "Scrap";
        }else if(scrapeType == AppConstants.SCRAP_TAUSIL_KATA){
//            getSupportActionBar().setTitle("Scrap-توصيل قطع");
            //title = "Scrap-توصيل قطع";
            title = "Scrap";

        }

        return title;
    }



    private void setMenuHolderFragment(){
        FrameLayout frameLayoutContainer = (FrameLayout) findViewById(R.id.framelayout_container);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_SCRAP);
        bundle.putInt(AppConstants.SCRAP_TYPE, scrapeType);
        LevelOneMenuActivityFragment fragment = new LevelOneMenuActivityFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();

    }

    @Override
    public void onMenuItemClick(HomeMenuItem homeMenuItem, int position) {
        Bundle bundle = new Bundle();
            if(scrapeType == AppConstants.SCRAP_MAIN){

                if(position == 0){
                    bundle.putInt(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_AMERICA);

                }else if(position == 1){
                    bundle.putInt(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_AWARBY);

                }else if(position == 2){
                    bundle.putInt(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_ASIBI);

                }else if(position == 3){
                    bundle.putInt(AppConstants.SCRAP_TYPE, AppConstants.SCRAP_TAUSIL_KATA);

                }

                Intent intent = new Intent(this, ScrapActivity.class);
                intent.putExtra(AppConstants.BUNDLE_EXTRA, bundle);

                startActivity(intent);
            }else if(scrapeType == AppConstants.SCRAP_AMERICA){
                System.gc();
                Intent intent = new Intent(this, CategorySaleListActivity.class);
                //startActivity(intent);
            }


    }
}


