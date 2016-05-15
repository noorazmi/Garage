package com.arsalan.garage.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.AccessoriesUserListData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AccessoriesUserListAdapter extends CustomRecyclerViewAdapter {

    List<AccessoriesUserListData.AccessoriesUserItem> mAccessoriesUserItems;
    private String mScrapType;
    private String mDescriptionLanguage;
    private Context mContext;


    public AccessoriesUserListAdapter(Context context, RecyclerView recyclerView, List<AccessoriesUserListData.AccessoriesUserItem> accessoriesUserItems, String scrapType, String descriptionLanguage) {
        super(recyclerView);
        this.mContext = context;
        this.mAccessoriesUserItems = accessoriesUserItems;
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
            final AccessoriesUserListData.AccessoriesUserItem model = mAccessoriesUserItems.get(position);
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            //holder.title.setText(model.getDescription());
            ((ListItemViewHolder) holder).phoneNumber.setText(model.getPhone());
            ((ListItemViewHolder) holder).title.setText(model.getDescription());
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(model.getImage(), listItemViewHolder.imgView, Utils.gerDisplayImageOptions());
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
        return getViewType(mAccessoriesUserItems, position);
    }

    @Override
    public int getItemCount() {
        return mAccessoriesUserItems.size();
    }


    public class ListItemViewHolder extends CustomViewHolder {

        ImageView imgView;
        TextView title;
        TextView phoneNumber;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imageview_icon);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            phoneNumber = (TextView) itemView.findViewById(R.id.textview_phone_number);
        }
    }
}
