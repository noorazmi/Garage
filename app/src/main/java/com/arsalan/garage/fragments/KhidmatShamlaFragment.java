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
 * <p/>
 * Created by: Noor  Alam on 15/10/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class KhidmatShamlaFragment extends Fragment {


    private static final String TAG = "LevelOneMenuActivityFragment";
    private ArrayList<HomeMenuItem> mMenuItemsArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private String mScreenType;
    private int mNumberOfColumns = 3;
    private Bundle mBundle;

    public KhidmatShamlaFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initMenuItems();
    }

    private void initMenuItems() {
        mMenuItemsArrayList = Utils.getKhidmatShamlaMenuItems();
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
                if (mMenuItemsArrayList != null && !mMenuItemsArrayList.isEmpty()) {

                    HomeMenuItem homeMenuItem = null;
                    if (mMenuItemsArrayList != null && !mMenuItemsArrayList.isEmpty()) {
                        homeMenuItem = mMenuItemsArrayList.get(position);
                    }

                    Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AppConstants.EXTRA_TITLE, homeMenuItem.getMenuTitle());
                    bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ARABIC);

                    switch (position) {
                        case 0:
                            bundle.putString(AppConstants.URL, Urls.HADADATWAMAJLAT);
                            break;
                        case 1:
                            bundle.putString(AppConstants.URL, Urls.TANKAD);
                            break;
                        case 2:
                            bundle.putString(AppConstants.URL, Urls.DARKATAR);
                            break;
                        case 3:
                            bundle.putString(AppConstants.URL, Urls.CRANE);
                            break;
                        case 4:
                            bundle.putString(AppConstants.URL, Urls.NASAAF);
                            break;
                        case 5:
                            bundle.putString(AppConstants.URL, Urls.NAKALAFSH);
                            break;
                        case 6:
                            bundle.putString(AppConstants.URL, Urls.TALEEMQAYADAT);
                            break;
                        case 7:
                            bundle.putString(AppConstants.URL, Urls.KAFALSHAYARAT);
                            break;
                        case 8:
                            bundle.putString(AppConstants.URL, Urls.ALSHAHNALBARI);
                            break;
                        default:
                            break;


                    }

                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);


                }
            }
        }));


        return rootView;
    }

}




