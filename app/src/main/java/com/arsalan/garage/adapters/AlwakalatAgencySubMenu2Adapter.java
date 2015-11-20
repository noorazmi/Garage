package com.arsalan.garage.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.HouseDisplayVo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 19/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class AlwakalatAgencySubMenu2Adapter extends RecyclerView.Adapter<AlwakalatAgencySubMenu2Adapter.ListItemViewHolder> {

    private ArrayList<HouseDisplayVo.CarModel> mHomeMenuItemArrayList;

    public AlwakalatAgencySubMenu2Adapter(ArrayList<HouseDisplayVo.CarModel> mHomeMenuItemArrayList) {
        this.mHomeMenuItemArrayList = mHomeMenuItemArrayList;
    }

    @Override
    public AlwakalatAgencySubMenu2Adapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlwakalatAgencySubMenu2Adapter.ListItemViewHolder holder, int position) {
        HouseDisplayVo.CarModel model = mHomeMenuItemArrayList.get(position);
        holder.title.setText(model.getModel());
        ImageLoader imageLoader = ImageLoader.getInstance();
        if(!TextUtils.isEmpty(model.getImage())){
            imageLoader.displayImage(model.getImage(), holder.imgView, Utils.gerDisplayImageOptions());
        }
    }

    @Override
    public int getItemCount() {
        return mHomeMenuItemArrayList.size();
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView;
        TextView title;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            imgView = (ImageView) itemView.findViewById(R.id.imageview_icon);
        }
    }
}
