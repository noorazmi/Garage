package com.arsalan.garage.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.arsalan.garage.R;
import com.arsalan.garage.fragments.EditPostFragment;

public class EditPostActivity extends BaseActivity{
    private static final String TAG = "PostAdTabFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setCustomTitleEnglish("Edit Post" , toolbar);
        setEditPostFragment();
    }

    private void setEditPostFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EditPostFragment fragment = new EditPostFragment();
        fragment.setArguments(getIntent().getExtras());
        fragmentTransaction.replace(R.id.framelayout_container, fragment).commit();
    }
}
