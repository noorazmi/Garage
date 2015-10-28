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

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class KarajatSubMenu1Fragment extends Fragment {


    private static final String TAG = "KarajatSubMenuFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    private static final int NUM_OF_ITEMS = 12;
    //private ArrayList<DataModel> mDataModels;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int addItemCount = 0;


    public KarajatSubMenu1Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_alwakalat_agency_menu, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        RecyclerView.LayoutManager layoutManager = null;

        layoutManager = new GridLayoutManager(getActivity(), NUM_OF_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

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
                if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
                    homeMenuItem = mHomeMenuItemArrayList.get(position);
                }

                Intent intent = new Intent(getActivity(), CategorySaleListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.EXTRA_TITLE, homeMenuItem.getMenuTitle());
                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ARABIC);

                switch (position) {
                    case 0:
                        bundle.putString(AppConstants.URL, Urls.MECHANIC);
                        break;
                    case 1:
                        bundle.putString(AppConstants.URL, Urls.JERAT);
                        break;
                    case 2:
                        bundle.putString(AppConstants.URL, Urls.SHABAGWAHADADA);
                        break;
                    case 3:
                        bundle.putString(AppConstants.URL, Urls.BREAKMIZANWAHEENA);
                        break;
                    case 4:
                        bundle.putString(AppConstants.URL, Urls.TANJEED);
                        break;
                    case 5:
                        bundle.putString(AppConstants.URL, Urls.ITARAAT);
                        break;
                    case 6:
                        bundle.putString(AppConstants.URL, Urls.ZAJAJ);
                        break;
                    case 7:
                        bundle.putString(AppConstants.URL, Urls.KHARBAAWATAKEEF);
                        break;
                    case 8:
                        bundle.putString(AppConstants.URL, Urls.KATAGYAAR);
                        break;
                    case 9:
                        bundle.putString(AppConstants.URL, Urls.BARMAJATSAYARAAT);
                        break;
                    case 10:
                        bundle.putString(AppConstants.URL, Urls.TADEELRANJAT);
                        break;
                    case 11:
                        bundle.putString(AppConstants.URL, Urls.MAKHRTH);
                        break;
                    case 12:
                        bundle.putString(AppConstants.URL, Urls.AWADAMWARADEETARAT);
                        break;
                    case 13:
                        bundle.putString(AppConstants.URL, Urls.TALEEMAYAADIN);
                        break;
                    case 14:
                        bundle.putString(AppConstants.URL, Urls.ZEENATSAYARATMASJALAT);
                        break;
                    case 15:
                        bundle.putString(AppConstants.URL, Urls.HYDRAULICS);
                        break;
                    case 16:
                        bundle.putString(AppConstants.URL, Urls.SHAFTSADMAT);
                        break;
                    default:
                        break;


                }

                intent.putExtras(bundle);
                getActivity().startActivity(intent);


            }
        }));

        return rootView;


    }


    private ArrayList<HomeMenuItem> getMenuItems() {

        mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage1, "ميكانيكي"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage2, "جيرات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage3, "صبغ وحداده"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage4, "بريك وهيئه"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage5, "تنجيد"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage6, "اطارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage7, "زجاج"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage8, "كهرباء وتكييف"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage9, "قطع غيار"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage10, "برمجة سيارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage11, "تعديل رنجات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage13, "مخرطه"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage14, "عوادم وراديترات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage15, "طلي معادن"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage16, "زينه سيارات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage20, "هايدروليك"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.grage21, "شفط صدمات"));

        return mHomeMenuItemArrayList;
    }

}



