package com.arsalan.garage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.models.HomeMenuItem;

import java.util.ArrayList;

/**
 * Created by noor on 05/05/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ListItemViewHolder> {



    private ArrayList<HomeMenuItem> mHomeMenuItemArrayList;

    public RecyclerViewAdapter(ArrayList<HomeMenuItem> mHomeMenuItemArrayList) {
        this.mHomeMenuItemArrayList = mHomeMenuItemArrayList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        HomeMenuItem model = mHomeMenuItemArrayList.get(position);
        holder.title.setText(model.getMenuTitle());
        holder.imgView.setImageResource(model.getImageId());
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
