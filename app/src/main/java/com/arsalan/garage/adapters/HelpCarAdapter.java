package com.arsalan.garage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.models.HelpCarItem;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 15/08/15.<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class HelpCarAdapter extends RecyclerView.Adapter<HelpCarAdapter.ListItemViewHolder> {



    private ArrayList<HelpCarItem> mHomeMenuItemArrayList;

    public HelpCarAdapter(ArrayList<HelpCarItem> mHomeMenuItemArrayList) {
        this.mHomeMenuItemArrayList = mHomeMenuItemArrayList;
    }


    @Override
    public HelpCarAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_car_help, parent, false);
        return new ListItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(HelpCarAdapter.ListItemViewHolder holder, int position) {
        HelpCarItem model = mHomeMenuItemArrayList.get(position);
        holder.phoneNumber.setText(model.getMobileNumber());
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

        TextView phoneNumber;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            phoneNumber = (TextView) itemView.findViewById(R.id.textview_mobile);
        }
    }
}
