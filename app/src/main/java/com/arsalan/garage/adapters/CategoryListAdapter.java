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
import com.arsalan.garage.vo.AmericanCarsVO;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 15/08/15.<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ListItemViewHolder> {

    private Context mContext;
    //private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private ArrayList<AmericanCarsVO.Result> mHomeMenuItemArrayList;
    private AmericanCarsVO americanCarsVO;


    //public CategoryListAdapter(ArrayList<HomeMenuItem> mHomeMenuItemArrayList, Context context) {
    public CategoryListAdapter(AmericanCarsVO americanCarsVO, Context context) {
        this.mHomeMenuItemArrayList = (ArrayList<AmericanCarsVO.Result>) americanCarsVO.getResults();
        this.mContext = context;

    }


    @Override
    public CategoryListAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale, parent, false);
        return new ListItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.ListItemViewHolder holder, int position) {
        AmericanCarsVO.Result model = mHomeMenuItemArrayList.get(position);
        holder.title.setText(model.getDescription());
        holder.phoneNumbet.setText(model.getPhone());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(model.getImage(), holder.imgView, Utils.gerDisplayImageOptions());
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


    public class ListItemViewHolder extends RecyclerView.ViewHolder {

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
