package com.arsalan.garage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.AccessoriesUserListActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesMenuFragment extends Fragment {


    private static final String TAG = "CrashedCarMenuFragment";
    private static final int NUM_OF_COLUMNS = 2;
    private ArrayList<HomeMenuItem> mMarineItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    public AccessoriesMenuFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_marine, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMNS);
        mRecyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(getMenuItems());
        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        /// RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(recyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                HomeMenuItem homeMenuItem = null;
                if (mMarineItemArrayList != null && !mMarineItemArrayList.isEmpty()) {
                    homeMenuItem = mMarineItemArrayList.get(position);
                }

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.SCREEN_TYPE, AppConstants.SCREEN_TYPE_MARINE);
                Intent intent = new Intent(getActivity(), AccessoriesUserListActivity.class);

                switch (position) {
                    case 0:
                        bundle.putString(AppConstants.URL, Urls.ACCESSORIESUSER_AMERICAN);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.american));
                        break;
                    case 1:
                        bundle.putString(AppConstants.URL, Urls.ACCESSORIESUSER_EUROPEAN);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.european));
                        break;
                    case 2:
                        bundle.putString(AppConstants.URL, Urls.ACCESSORIESUSER_ASIAN);
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.asian));
                        break;
                    case 3:
                        bundle.putString(AppConstants.URL, Urls.ACCESSORIESUSER_OTHER);
                        //bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.alkaswarat));
                        bundle.putString(AppConstants.EXTRA_TITLE, getString(R.string.accessories));
                        break;
                    default:
                        break;
                }

                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        }));

        return rootView;


    }


    private ArrayList<HomeMenuItem> getMenuItems() {

        mMarineItemArrayList = new ArrayList<>();
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.american, getString(R.string.american)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.europian, getString(R.string.european)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.asian, getString(R.string.asian)));
        mMarineItemArrayList.add(new HomeMenuItem(R.drawable.accessories, getString(R.string.accessories)));

        return mMarineItemArrayList;
    }

}

