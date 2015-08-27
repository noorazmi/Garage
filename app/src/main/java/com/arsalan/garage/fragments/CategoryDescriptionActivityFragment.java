package com.arsalan.garage.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryDescriptionActivityFragment extends Fragment {

    public CategoryDescriptionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_description, container, false);
    }
}
