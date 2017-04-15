package com.arsalan.garage.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.activities.CategoryDescriptionActivity;
import com.arsalan.garage.activities.CategorySaleListActivity;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.AmericanCarsVO;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 11/02/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class CategorySaleListAdapter extends CustomRecyclerViewAdapter implements View.OnClickListener {

    private List<AmericanCarsVO.Result> mHomeMenuItemArrayList;
    private String mScrapType;
    private String mDescriptionLanguage;
    private Context mContext;


    public CategorySaleListAdapter(Context context, RecyclerView recyclerView, List<AmericanCarsVO.Result> mCarList, String scrapType, String descriptionLanguage) {
        super(recyclerView);
        this.mContext = context;
        this.mHomeMenuItemArrayList = mCarList;
        this.mScrapType = scrapType;
        this.mDescriptionLanguage = descriptionLanguage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (isItemView(viewType)) {
            View itemView = null;
            if (!TextUtils.isEmpty(mDescriptionLanguage) && mDescriptionLanguage.equals(AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH)) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale_english, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale, parent, false);
            }
            viewHolder = new ListItemViewHolder(itemView);

        } else {
            viewHolder = getProgressViewHolder(parent);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (!isProgerssViewHolder(holder)) {

            final AmericanCarsVO.Result model = mHomeMenuItemArrayList.get(position);
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            listItemViewHolder.date.setVisibility(View.GONE);

            ((ListItemViewHolder) holder).phoneNumbet.setText(model.getPhone());
            ((ListItemViewHolder) holder).phoneNumbet.setTag(model.getPhone());
            ((ListItemViewHolder) holder).phoneNumbet.setOnClickListener(this);

            holder.itemView.setTag(R.id.textview_phone_number, position);
            holder.itemView.setOnClickListener(this);

            if (mScrapType != null && mScrapType.equals(AppConstants.SCRAP_DELIVERY)) {
                listItemViewHolder.title.setText(GarageApp.getInstance().getString(R.string.delivery));
            } else {
                ((ListItemViewHolder) holder).title.setText(model.getDescription());
            }

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(model.getImage(), listItemViewHolder.imgView, Utils.gerDisplayImageOptions());
        } else {
            showProgressBar(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //if you are using multiple view types than write your logic here and
        // whatever integer you are going to return as per your logic instead of
        // that return getViewType(THE_VALUE_TO_BE_RETURN);
        //if you are using single view type use follows:
        return getViewType(mHomeMenuItemArrayList, position);
    }

    @Override
    public int getItemCount() {
        return mHomeMenuItemArrayList.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textview_phone_number) {
            String phoneNumber = v.getTag().toString();
            Utils.initCall(phoneNumber, mContext);
        } else {
            int position = (int) v.getTag(R.id.textview_phone_number);
            if (AppConstants.SCRAP_DELIVERY.equals(((CategorySaleListActivity) mContext).getIntent().getExtras().getString(AppConstants.SCRAP_TYPE))) {
                return;
            }
            Intent intent = new Intent(mContext, CategoryDescriptionActivity.class);
            Bundle bundle = new Bundle();
            AmericanCarsVO.Result result = mHomeMenuItemArrayList.get(position);
            bundle.putString(AppConstants.DESCRIPTION, result.getDescription());
            bundle.putString(AppConstants.IMAGE_URL, result.getImage());
            bundle.putString(AppConstants.PHONE_NUMBER, result.getPhone());
            bundle.putString(AppConstants.ID, result.getItem_id());
            bundle.putString(AppConstants.EXTRA_TITLE, ((CategorySaleListActivity) mContext).getIntent().getStringExtra(AppConstants.EXTRA_TITLE));
            bundle.putString(AppConstants.EXTRA_DESCRIPTION_LANGUAGE, ((CategorySaleListActivity) mContext).getIntent().getStringExtra(AppConstants.EXTRA_DESCRIPTION_LANGUAGE));
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    public class ListItemViewHolder extends CustomViewHolder {
        ImageView imgView;
        TextView title;
        TextView phoneNumbet;
        TextView date;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imageview_icon);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            phoneNumbet = (TextView) itemView.findViewById(R.id.textview_phone_number);
            date = (TextView) itemView.findViewById(R.id.textview_date);
        }
    }


    public void animateTo(List<AmericanCarsVO.Result> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<AmericanCarsVO.Result> newModels) {
        for (int i = mHomeMenuItemArrayList.size() - 1; i >= 0; i--) {
            final AmericanCarsVO.Result model = mHomeMenuItemArrayList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<AmericanCarsVO.Result> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final AmericanCarsVO.Result model = newModels.get(i);
            if (!mHomeMenuItemArrayList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<AmericanCarsVO.Result> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final AmericanCarsVO.Result model = newModels.get(toPosition);
            final int fromPosition = mHomeMenuItemArrayList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public AmericanCarsVO.Result removeItem(int position) {
        final AmericanCarsVO.Result model = mHomeMenuItemArrayList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, AmericanCarsVO.Result model) {
        mHomeMenuItemArrayList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final AmericanCarsVO.Result model = mHomeMenuItemArrayList.remove(fromPosition);
        mHomeMenuItemArrayList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
