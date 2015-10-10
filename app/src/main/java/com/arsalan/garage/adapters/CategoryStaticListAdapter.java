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
import com.arsalan.garage.models.HomeMenuItem;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.vo.AmericanCarsVO;

import java.util.ArrayList;

public class CategoryStaticListAdapter extends RecyclerView.Adapter<CategoryStaticListAdapter.ListItemViewHolder> {

    private Context mContext;
    //private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private AmericanCarsVO americanCarsVO;
    private String mScrapType;
    private String mDescriptionLanguage;


    //public CategoryListAdapter(ArrayList<HomeMenuItem> mHomeMenuItemArrayList, Context context) {
    public CategoryStaticListAdapter(ArrayList<HomeMenuItem> menuItemArrayList, Context context, String descriptionLanguage) {
        this.mHomeMenuItemArrayList = (ArrayList<HomeMenuItem>) menuItemArrayList;
        this.mContext = context;
        this.mDescriptionLanguage = descriptionLanguage;

    }


    @Override
    public CategoryStaticListAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if(!TextUtils.isEmpty(mDescriptionLanguage) && mDescriptionLanguage.equals(AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH)){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale_english, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale, parent, false);
        }
        return new ListItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CategoryStaticListAdapter.ListItemViewHolder holder, int position) {
        final HomeMenuItem model = mHomeMenuItemArrayList.get(position);
        holder.title.setText(model.getMenuTitle());
        holder.imgView.setImageResource(model.getImageId());
        holder.phoneNumbet.setVisibility(View.GONE);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mHomeMenuItemArrayList.size();
    }


    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

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

