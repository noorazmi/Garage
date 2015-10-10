package com.arsalan.garage.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CategorySaleStaticListActivity;
import com.arsalan.garage.adapters.CategoryStaticListAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.vo.AmericanCarsVO;

import java.util.ArrayList;

public class CategorySaleListStaticFragment extends Fragment {




    private static final String TAG = "PlaceholderFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    private static final int NUM_OF_ITEMS = 6;
    private RecyclerView mRecyclerView;
    private CategoryStaticListAdapter mCategoryListAdapter;
    private int addItemCount = 0;
    AmericanCarsVO mAmericanCarsVO;

    public CategorySaleListStaticFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_category_sale_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        setAdapter(getVoObject(getArguments().getString(AppConstants.URL)));
        return rootView;
    }

    private  ArrayList<HomeMenuItem> getVoObject(String url){
        if(url.equals(Urls.ALWAKALAT_AGENCIES)){
            return getMenuAlkarajatAgenciesItems();
        }

        return  null;
    }

    private void setAdapter( ArrayList<HomeMenuItem>  americanCarsVO){
        ((CategorySaleStaticListActivity)getActivity()).setNoOfItemsInTooBar(americanCarsVO.size());
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        mCategoryListAdapter = new CategoryStaticListAdapter( americanCarsVO, getActivity(), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));

        /*Third party ItemDecoration found from https://gist.github.com/alexfu/0f464fc3742f134ccd1e*/
        //RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        // RecyclerView.ItemDecoration horizontalDivider = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST);

        RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(1);
        //RecyclerView.ItemDecoration horizontalDivider = new DividerDecoration(getActivity(), DividerDecoration.VERTICAL_LIST);
        //mRecyclerView.addItemDecoration(horizontalDivider);
        //mRecyclerView.addItemDecoration(verticalDivider);

        // this is the default;
        // this call is actually only necessary with custom ItemAnimators
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mCategoryListAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                if(AppConstants.SCRAP_DELIVERY.equals(getArguments().getString(AppConstants.SCRAP_TYPE))){
//                    return;
//                }
//                //if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
//                Intent intent = new Intent(getActivity(), CategoryDescriptionActivity.class);
//                Bundle bundle = new Bundle();
//                AmericanCarsVO.Result result = mAmericanCarsVO.getResults().get(position);
//                bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
//                bundle.putString(AppConstants.IMAGE_URL, result.getImage());
//                bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
//                bundle.putString(AppConstants.ID, result.getItem_id());
//                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
//                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
//                intent.putExtras(bundle);
                //Logger.i("UserId", mHomeMenuItemArrayList.get(position).getMenuTitle());
                //getActivity().startActivity(intent);
                //}
            }
        }));

    }

    @Override
    public void onResume() {
        super.onResume();
    }




    private ArrayList<HomeMenuItem> getMenuAlkarajatAgenciesItems() {

        ArrayList<HomeMenuItem> mHomeMenuItemArrayList = new ArrayList<>();
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gmc0, "كراجات"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gmc1, "المساعده على الطريق"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gmc2, "السكراب"));
        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.gmc3, "تكسي"));
        return mHomeMenuItemArrayList;
    }


}



