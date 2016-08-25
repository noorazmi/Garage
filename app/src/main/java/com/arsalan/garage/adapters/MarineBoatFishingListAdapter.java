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
import com.arsalan.garage.models.BoatFishingListVO;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Created by: Noor  Alam on 07/08/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class MarineBoatFishingListAdapter extends CustomRecyclerViewAdapter {

    private ArrayList<BoatFishingListVO.BoatFishing> mBoatFishingArrayList;
    private Context mContext;


    public MarineBoatFishingListAdapter(Context context, RecyclerView recyclerView, ArrayList<BoatFishingListVO.BoatFishing> boarFishingArrayList) {
        super(recyclerView);
        this.mContext = context;
        this.mBoatFishingArrayList = boarFishingArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if (isItemView(viewType)) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_wash_list_item, parent, false);
            viewHolder = new ListItemViewHolder(itemView);
        } else {
            viewHolder = getProgressViewHolder(parent);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (!isProgerssViewHolder(holder)) {
            BoatFishingListVO.BoatFishing model = mBoatFishingArrayList.get(position);
            ((ListItemViewHolder)holder).title.setText(model.getContact());
            //ImageLoader imageLoader = ImageLoader.getInstance();
            //holder.imgView.getLayoutParams().width = 500;
            //holder.imgView.getLayoutParams().height = 304;

            //RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams)holder.imgView.getLayoutParams();
            //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            //holder.imgView.setLayoutParams(layoutParams);
            if(model.getPhone()!= null && !TextUtils.isEmpty(model.getPhone())){
                ((ListItemViewHolder)holder).textViewPhone.setText(model.getPhone());
            }

            if(!TextUtils.isEmpty(model.getImage())){
                //imageLoader.displayImage(model.getImage(), holder.imgView, Utils.gerDisplayImageOptions());
                Glide.with(mContext)
                        .load(model.getImage()).placeholder(R.mipmap.ic_launcher)
                        //.override(500, 304)
                        .into(((ListItemViewHolder)holder).imgView);
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
        return getViewType(mBoatFishingArrayList, position);
    }

    @Override
    public int getItemCount() {
        return mBoatFishingArrayList.size();
    }


    public class ListItemViewHolder extends CustomViewHolder {

        ImageView imgView;
        TextView title;
        TextView textViewPhone;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textview_title);
            imgView = (ImageView) itemView.findViewById(R.id.imageview_icon);
            textViewPhone = (TextView) itemView.findViewById(R.id.textview_phone1);
        }
    }


    public void animateTo(List<BoatFishingListVO.BoatFishing> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<BoatFishingListVO.BoatFishing> newModels) {
        for (int i = mBoatFishingArrayList.size() - 1; i >= 0; i--) {
            final BoatFishingListVO.BoatFishing model = mBoatFishingArrayList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<BoatFishingListVO.BoatFishing> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final BoatFishingListVO.BoatFishing model = newModels.get(i);
            if (!mBoatFishingArrayList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<BoatFishingListVO.BoatFishing> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final BoatFishingListVO.BoatFishing model = newModels.get(toPosition);
            final int fromPosition = mBoatFishingArrayList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public BoatFishingListVO.BoatFishing removeItem(int position) {
        final BoatFishingListVO.BoatFishing model = mBoatFishingArrayList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, BoatFishingListVO.BoatFishing model) {
        mBoatFishingArrayList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final BoatFishingListVO.BoatFishing model = mBoatFishingArrayList.remove(fromPosition);
        mBoatFishingArrayList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
