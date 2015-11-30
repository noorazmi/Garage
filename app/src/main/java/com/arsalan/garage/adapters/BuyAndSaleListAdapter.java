package com.arsalan.garage.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.AmericanCarsVO;
import com.arsalan.garage.vo.BuyAndSaleListItemVo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class BuyAndSaleListAdapter extends RecyclerView.Adapter<BuyAndSaleListAdapter.ListItemViewHolder> {

    private Context mContext;
    //private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;
    private ArrayList<BuyAndSaleListItemVo.BuySaleItem> mHomeMenuItemArrayList;
    private AmericanCarsVO americanCarsVO;
    private String mScrapType;
    private String mDescriptionLanguage;


    //public CategoryListAdapter(ArrayList<HomeMenuItem> mHomeMenuItemArrayList, Context context) {
    public BuyAndSaleListAdapter(BuyAndSaleListItemVo americanCarsVO, Context context, String scrapType, String descriptionLanguage) {
        this.mHomeMenuItemArrayList = (ArrayList<BuyAndSaleListItemVo.BuySaleItem>) americanCarsVO.getResults();
        this.mContext = context;
        this.mScrapType = scrapType;
        this.mDescriptionLanguage = descriptionLanguage;

    }


    @Override
    public BuyAndSaleListAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
//        if(!TextUtils.isEmpty(mDescriptionLanguage) && mDescriptionLanguage.equals(AppConstants.EXTRA_DESCRIPTION_LANGUAGE_ENGLISH)){
//            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category_sale_english, parent, false);
//        }else {
//            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buy_and_sale, parent, false);
//        }
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buy_and_sale, parent, false);
        return new ListItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(BuyAndSaleListAdapter.ListItemViewHolder holder, int position) {
        final BuyAndSaleListItemVo.BuySaleItem model = mHomeMenuItemArrayList.get(position);
        //holder.title.setText(model.getDescription());
        holder.phoneNumbet.setText(model.getPhone());
        if(mScrapType != null && mScrapType.equals(AppConstants.SCRAP_DELIVERY)){
            holder.title.setText(mContext.getString(R.string.car_delivery));
            holder.phoneNumbet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.initCall(model.getPhone(), mContext);
                }
            });
        }else{
            holder.title.setText(model.getDescription());
            holder.phoneNumbet.setOnClickListener(null);
        }


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
