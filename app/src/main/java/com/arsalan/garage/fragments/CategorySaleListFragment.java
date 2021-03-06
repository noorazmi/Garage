package com.arsalan.garage.fragments;


import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.arsalan.garage.R;
import com.arsalan.garage.activities.CategorySaleListActivity;
import com.arsalan.garage.adapters.CategorySaleListAdapter;
import com.arsalan.garage.adapters.CustomRecyclerViewAdapter;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.DividerItemDecoration;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.AmericanCarsVO;

import java.util.ArrayList;
import java.util.List;

import networking.HttpConstants;
import networking.listeners.OnLoadCompleteListener;
import networking.loader.LoaderHandler;
import networking.models.HTTPModel;
import networking.models.HTTPRequest;
import networking.models.HTTPResponse;

public class CategorySaleListFragment extends Fragment {

    private static final String TAG = "CategorySaleFragment";
    private RecyclerView mRecyclerView;
    private CategorySaleListAdapter mCategoryListAdapter;
    private AmericanCarsVO mAmericanCarsVO;
    private List<AmericanCarsVO.Result> mCarList;
    protected ArrayList<AmericanCarsVO.Result> mCarListReserved;
    private int pageNumber = 0;
    private boolean isFirstTime = true;
    private boolean keepLoading = true;
    private EditText editTextSearch;
    private ImageView imageViewSearchLogo;
    protected int mTotalItemCount;


    public CategorySaleListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category_sale_list, container, false);
        editTextSearch = (EditText)rootView.findViewById(R.id.edittext_search);
        imageViewSearchLogo = (ImageView)rootView.findViewById(R.id.imageview_search_logo);
        imageViewSearchLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextSearch.getText().toString().length() > 0){
                    editTextSearch.getText().clear();
                    mCarList.clear();
                    mCarList.addAll(mCarListReserved);
                    mCategoryListAdapter.notifyDataSetChanged();
                }
            }
        });
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        if (Utils.isNetworkAvailable(getActivity())) {
            setTotalCount();
        } else {
            Utils.showSnackBar(getActivity(), getString(R.string.no_network_connection));
        }
        setAdapter();
        return rootView;
    }


    private void setAdapter() {

        RecyclerView.LayoutManager layoutManager = null;
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        //mDataModels = getDataModelList();

        //mCategoryListAdapter = new CategoryListAdapter(americanCarsVO, getActivity().getIntent().getStringExtra(AppConstants.SCRAP_TYPE), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
        mCarListReserved = new ArrayList<>(0);
        mCarList = new ArrayList<>(0);
        //dummyItems = new ArrayList<>(0);
        mCategoryListAdapter = new CategorySaleListAdapter(getActivity(), mRecyclerView, mCarList, getActivity().getIntent().getStringExtra(AppConstants.SCRAP_TYPE), getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));


        RecyclerView.ItemDecoration verticalDivider = new DividerItemDecoration(1);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCategoryListAdapter);
        mCategoryListAdapter.setOnPullUpListener(new CustomRecyclerViewAdapter.OnLoadMoreListener()
        {
            @Override
            public void onLoadMore() {
                if(keepLoading){
                    mCategoryListAdapter.setDownloadingProgress(true,mCarList);
                    performGET();
                }
            }
        });

//        mCategoryListAdapter.addOnClickCallback(new CustomRecyclerViewAdapter.OnClickCallback() {
//            @Override
//            public void onViewClickListener(View v, int position) {
//                Toast.makeText(GarageApp.getInstance(),"hello "+position,Toast.LENGTH_SHORT).show();
//            }
//        });

//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                if (AppConstants.SCRAP_DELIVERY.equals(getArguments().getString(AppConstants.SCRAP_TYPE))) {
//                    return;
//                }
//                Intent intent = new Intent(getActivity(), CategoryDescriptionActivity.class);
//                Bundle bundle = new Bundle();
//                AmericanCarsVO.Result result = mCarList.get(position);
//                bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
//                bundle.putString(AppConstants.IMAGE_URL, result.getImage());
//                bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
//                bundle.putString(AppConstants.ID, result.getItem_id());
//                bundle.putString(AppConstants.EXTRA_TITLE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
//                bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, getActivity().getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
//            }
//        }));
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSearch();
    }

    private void setSearch(){
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<AmericanCarsVO.Result> filteredModelList = filter(mCarListReserved, s.toString());
                mCategoryListAdapter.animateTo(filteredModelList);
                if(s.toString().length() > 0){
                    imageViewSearchLogo.setImageResource(R.drawable.ic_cross_blue);
                }else {
                    imageViewSearchLogo.setImageResource(R.drawable.ic_search_hover);
                }
                mRecyclerView.scrollToPosition(0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private List<AmericanCarsVO.Result> filter(List<AmericanCarsVO.Result> models, String query) {
        query = query.toLowerCase();

        final List<AmericanCarsVO.Result> filteredModelList = new ArrayList<>();
        for (AmericanCarsVO.Result model : models) {
            //final String text = model.getDescription();
            final String text = ""+model.getPhone();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



    private void showData(List<AmericanCarsVO.Result> carList){
        mCategoryListAdapter.setDownloadingProgress(false,mCarList);
        mCarList.addAll(carList);
        mCarListReserved.addAll(carList);
        if (mCarListReserved.size() >= mTotalItemCount) {
            keepLoading = false;
        }
        mCategoryListAdapter.notifyDataSetChanged();
        mCategoryListAdapter.setLoaded();
    }

    private void performGET() {
        if (!Utils.isNetworkAvailable()) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("There is network or server problem. Please try again.");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    });
            alertDialog.show();
            return;
        }

        HTTPRequest httpRequest = new HTTPRequest();
        if(isFirstTime){
            httpRequest.setShowProgressDialog(true);
            isFirstTime = false;
        }

        Bundle bundle = getArguments();
        Log.e(TAG, " ******^^^^^^^^^bundle URL:" + bundle.getString(AppConstants.URL));
        httpRequest.setUrl(getArguments().getString(AppConstants.URL) + "?page="+(++pageNumber)+"&limit="+ AppConstants.REQUEST_ITEM_COUNT);
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(AmericanCarsVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mAmericanCarsVO = (AmericanCarsVO) httpResponse.getValueObject();
                showData(mAmericanCarsVO.getResults());
                Logger.i(TAG, "***** GET | onLoadComplete() | loaderId:" + httpResponse.getLoaderId() + "|responseJSONString:" + httpResponse.getResponseJSONString());
            }
        });
        loaderHandler.loadData();
    }

    private void setTotalCount(){

        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.setShowProgressDialog(true);
        httpRequest.setUrl(getArguments().getString(AppConstants.URL) + "?page=all");
        httpRequest.setRequestType(HttpConstants.HTTP_REQUEST_TYPE_GET);
        httpRequest.setValueObjectFullyQualifiedName(AmericanCarsVO.class.getName());
        LoaderHandler loaderHandler = LoaderHandler.newInstance(this, httpRequest);
        loaderHandler.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(HTTPModel httpModel) {
                HTTPResponse httpResponse = (HTTPResponse) httpModel;
                mAmericanCarsVO = (AmericanCarsVO) httpResponse.getValueObject();
                if(mAmericanCarsVO == null){
                    return;
                }else if(mAmericanCarsVO.getResults() == null){
                    return;
                }

                mTotalItemCount = mAmericanCarsVO.getData_count();
                ((CategorySaleListActivity) getActivity()).setNoOfItemsInTooBar(mTotalItemCount);
                if(mTotalItemCount >= 0){
                    performGET();
                }
            }
        });
        loaderHandler.loadData();
    }
}
