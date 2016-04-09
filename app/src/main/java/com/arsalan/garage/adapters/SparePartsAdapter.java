package com.arsalan.garage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.vo.SparePartsVo;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 08/04/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class SparePartsAdapter extends RecyclerView.Adapter<SparePartsAdapter.ListItemViewHolder> {

    private ArrayList<SparePartsVo.SparePart> mSparePartArrayList;

    public SparePartsAdapter(ArrayList<SparePartsVo.SparePart> sparePartArrayList) {
        this.mSparePartArrayList = sparePartArrayList;
    }

    @Override
    public SparePartsAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_spare_part, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SparePartsAdapter.ListItemViewHolder holder, int position) {
        SparePartsVo.SparePart model = mSparePartArrayList.get(position);
        holder.textViewBranchName.setText(model.getBranch_name());

        holder.textViewPhone.setText(model.getPhone());

    }

    @Override
    public int getItemCount() {
        return mSparePartArrayList.size();
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewBranchName;
        TextView textViewPhone;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            textViewBranchName = (TextView) itemView.findViewById(R.id.textview_branch_name);
            textViewPhone = (TextView) itemView.findViewById(R.id.textview_phone);
        }
    }
}
