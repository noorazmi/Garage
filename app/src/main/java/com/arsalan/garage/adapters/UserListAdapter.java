package com.arsalan.garage.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.UserListItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 14/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class UserListAdapter extends CustomRecyclerViewAdapter implements View.OnClickListener{

    private List<UserListItem> mAccessoriesUserItems;
    private String mDescriptionLanguage;
    private Context mContext;
    private OnUserListItemClickListener mOnUserListItemClickListener;

    public UserListAdapter(Context context, RecyclerView recyclerView, List<UserListItem> accessoriesUserItems, String descriptionLanguage) {
        super(recyclerView);
        this.mContext = context;
        this.mAccessoriesUserItems = accessoriesUserItems;
        this.mDescriptionLanguage = descriptionLanguage;
    }

    public void setOnUserListItemClickListener(OnUserListItemClickListener onUserListItemClickListener){
        mOnUserListItemClickListener = onUserListItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (isItemView(viewType)) {
            //In case of multiple/single view type put your code logic here...
            View itemView = null;
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale_english, parent, false);
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
            final UserListItem model = mAccessoriesUserItems.get(position);
            ListItemViewHolder listItemViewHolder = (ListItemViewHolder) holder;
            holder.itemView.setTag(R.id.textview_phone_number, position);
            holder.itemView.setOnClickListener(this);
            ((ListItemViewHolder) holder).date.setText(model.getPost_date());
            ((ListItemViewHolder) holder).title.setText(model.getTitle());
            ((ListItemViewHolder) holder).phoneNumber.setText(model.getDescription());
            ((ListItemViewHolder) holder).phoneNumber.setTag(R.id.textview_phone_number, position);
            ((ListItemViewHolder) holder).phoneNumber.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        int position = (int) v.getTag(R.id.textview_phone_number);
        if (v.getId() == R.id.textview_phone_number) {
            if(mOnUserListItemClickListener != null){
                mOnUserListItemClickListener.onUserListPhoneNumberClick(position);
            }
        } else {
            if(mOnUserListItemClickListener != null){
                mOnUserListItemClickListener.onUserListItemClick(position);
            }
        }
    }


    public class ListItemViewHolder extends CustomViewHolder {

        ImageView imgView;
        TextView date;
        TextView title;
        TextView phoneNumber;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            imgView = (ImageView) itemView.findViewById(R.id.imageview_icon);
            date = (TextView) itemView.findViewById(R.id.textview_date);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            phoneNumber = (TextView) itemView.findViewById(R.id.textview_phone_number);
        }
    }

    public interface OnUserListItemClickListener{
        void onUserListPhoneNumberClick(int position);
        void onUserListItemClick(int position);
    }
}
