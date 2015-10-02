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
import com.arsalan.garage.activities.CategorySaleListActivity;
import com.arsalan.garage.adapters.RecyclerViewAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpOnRoadFragment extends Fragment {


    private static final String TAG = "HelpOnRoadFragment";
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int mNumberOfColumns;
    private Bundle mBundle;

    public HelpOnRoadFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        mNumberOfColumns = AppConstants.NUM_OF_COLUMNS;
        mMenuItemsArrayList = Utils.getRoadHelpScreenMenuItems(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new GridLayoutManager(getActivity(), mNumberOfColumns);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        recyclerViewAdapter = new RecyclerViewAdapter(mMenuItemsArrayList);

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        //RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(AppConstants.DIVIDER_ITEM_WIDTH);
        //RecyclerView.ItemDecoration verticalDivider  = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        //RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST);
        mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(recyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                String url = null;
                switch (position) {
                    case 0:
                        url = Urls.TAREEQALSALMI;
                        break;
                    case 1:
                        url = Urls.TAREEQALSABEEH;
                        break;
                    case 2:
                        url = Urls.TAREEQALABDALI;
                        break;
                    case 3:
                        url = Urls.TAREEQALKABAD;
                        break;
                    case 4:
                        url = Urls.TAREEQALWAFRATWAALNUWAISIB;
                        break;
                    case 5:
                        url = Urls.ALASMAH;
                        break;
                    case 6:
                        url = Urls.ALJAHRA;
                        break;
                    case 7:
                        url = Urls.ALFARWANIYA;
                        break;
                    case 8:
                        url = Urls.ALAHMADI;
                        break;
                    case 9:
                        url = Urls.HAULI;
                        break;
                    case 10:
                        url = Urls.MUBARAKALKABEER;
                        break;
                    default:
                        break;


                }

                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.URL, url  );
                bundle.putString(AppConstants.EXTRA_TITLE, ((HomeMenuItem)mMenuItemsArrayList.get(position)).getMenuTitle());
                Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }));


        return rootView;
    }

}



