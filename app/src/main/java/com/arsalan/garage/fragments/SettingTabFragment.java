package com.arsalan.garage.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.arsalan.garage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingTabFragment extends Fragment {

    private static final String TAG = "LeaderBoardFragment";
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private View leaderBoardListHeader;
    boolean isLeaderBoardDataLoaded = false;

    public SettingTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting_tab, container, false);
        return rootView;
    }
}
