package com.arsalan.garage.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.GarageApp;
import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.MarineUserListData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 13/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineUserListAdapter extends CustomRecyclerViewAdapter {

    private ArrayList<MarineUserListData.MarineUserItem> mHomeMenuItemArrayList;
    private String mScrapType;
    private String mDescriptionLanguage;
    private Context mContext;


    public MarineUserListAdapter(Context context, RecyclerView recyclerView, List<MarineUserListData.MarineUserItem> mCarList, String scrapType, String descriptionLanguage) {
        super(recyclerView);
        this.mContext = context;
        this.mHomeMenuItemArrayList = (ArrayList<MarineUserListData.MarineUserItem>) mCarList;
        this.mScrapType = scrapType;
        this.mDescriptionLanguage = descriptionLanguage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (isItemView(viewType)) {
            //In case of multiple/single view type put your code logic here...
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
            //In case of multiple/single view type put your code logic here...
            final MarineUserListData.MarineUserItem model = mHomeMenuItemArrayList.get(position);
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            //holder.title.setText(model.getDescription());
            ((ListItemViewHolder) holder).phoneNumbet.setText(model.getPhone());
            if (mScrapType != null && mScrapType.equals(AppConstants.SCRAP_DELIVERY)) {
                listItemViewHolder.title.setText(GarageApp.getInstance().getString(R.string.delivery));
            /*listItemViewHolder.phoneNumbet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.initCall(model.getPhone(), GarageApp.getInstance());
                }
            });*/
            } else {
                ((ListItemViewHolder) holder).title.setText(model.getDescription());
                //((CustomViewHolder)listItemViewHolder).setOnClickListener(null);
            }


            ImageLoader imageLoader = ImageLoader.getInstance();
            if(model.getDescription().contains("wash") || model.getDescription().contains("shower") || model.getDescription().contains("clean")){
                listItemViewHolder.imgView.setImageResource(R.drawable.carwash_center);
                listItemViewHolder.imgView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }else {
                imageLoader.displayImage(model.getImage(), listItemViewHolder.imgView, Utils.gerDisplayImageOptions());
            }
        }else{
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


    public class ListItemViewHolder extends CustomViewHolder {

        ImageView imgView;
        TextView title;
        TextView phoneNumbet;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imageview_icon);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            phoneNumbet = (TextView) itemView.findViewById(R.id.textview_phone_number);
        }
    }
}
