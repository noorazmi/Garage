package com.arsalan.garage.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CategoryDescriptionActivity;
import com.arsalan.garage.adapters.CategoryListAdapter;
import com.arsalan.garage.interfaces.ClickListener;
import com.arsalan.garage.interfaces.RecyclerTouchListener;
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.vo.AmericanCarsVO;

import java.util.ArrayList;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategorySaleListActivityFragment extends Fragment {




    private static final String TAG = "PlaceholderFragment";
    /*Number of columns in the grid view*/
    private static final int NUM_OF_COLUMNS = 3;
    /*Total number of items in the RecyclerView*/
    private static final int NUM_OF_ITEMS = 6;
    //private ArrayList<DataModel> mDataModels;
//    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private RecyclerView mRecyclerView;
    private CategoryListAdapter mCategoryListAdapter;
    private int addItemCount = 0;
    AmericanCarsVO mAmericanCarsVO;

    public CategorySaleListActivityFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Must be set in order to capture menu item click events. If you don't set it, it will not show the menu items set in the Activity holding this fragment.
        View rootView = inflater.inflate(R.layout.fragment_category_sale_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        return rootView;
    }




//    private ArrayList<HomeMenuItem> getMenuItems() {
//
//        mHomeMenuItemArrayList = new ArrayList<>();
//
//        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.scarptest, "مؤسسة المناور لسكراب كاديلاك شفر جمس"));
//        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.car_red, "مؤسسة المناور لسكراب كاديلاك شفر جمس"));
//        mHomeMenuItemArrayList.add(new HomeMenuItem(R.drawable.mobile_sample, "مؤسسة المناور لسكراب كاديلاك شفر جمس"));
//        return mHomeMenuItemArrayList;
//    }


    private void setAdapter(AmericanCarsVO americanCarsVO ){
        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        mCategoryListAdapter = new CategoryListAdapter( americanCarsVO, getActivity());

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
                //if (mHomeMenuItemArrayList != null && !mHomeMenuItemArrayList.isEmpty()) {
                    Intent intent = new Intent(getActivity(), CategoryDescriptionActivity.class);
                    Bundle bundle = new Bundle();
                    AmericanCarsVO.Result result = mAmericanCarsVO.getResults().get(position);
                    bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
                    bundle.putString(AppConstants.IMAGE_URL, result.getImage());
                    bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
                    bundle.putString(AppConstants.ID, result.getItem_id());
                    intent.putExtras(bundle);
                    //Logger.i("UserId", mHomeMenuItemArrayList.get(position).getMenuTitle());
                    getActivity().startActivity(intent);
                //}
            }
        }));

    }

    @Override
    public void onResume() {
        super.onResume();
        performGET();
    }

    private void performGET(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(false);
        Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + bundle.getString(AppConstants.URL));
        httpRequest.setUrl(getArguments().getString(AppConstants.URL));
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(AmericanCarsVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mAmericanCarsVO = (AmericanCarsVO) httpResponse.getValueObject();
                setAdapter(mAmericanCarsVO);


                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }





}
