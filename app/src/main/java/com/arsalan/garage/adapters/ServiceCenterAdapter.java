package com.arsalan.garage.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.vo.SparePartsVo;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 09/04/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ServiceCenterAdapter extends RecyclerView.Adapter<ServiceCenterAdapter.ListItemViewHolder> {

    private final Context mContext;
    private ArrayList<SparePartsVo.SparePart> mSparePartArrayList;

    public ServiceCenterAdapter(ArrayList<SparePartsVo.SparePart> sparePartArrayList, Context context) {
        this.mSparePartArrayList = sparePartArrayList;
        this.mContext = context;
    }

    @Override
    public ServiceCenterAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_service_center, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceCenterAdapter.ListItemViewHolder holder, int position) {
        final SparePartsVo.SparePart model = mSparePartArrayList.get(position);
        holder.textViewAddress.setText(model.getAddress());

        holder.textViewPhone.setText(model.getPhone());
        if(!TextUtils.isEmpty(model.getWorking_time())){
            holder.textViewTimings.setVisibility(View.VISIBLE);
            holder.textViewTimings.setText(model.getWorking_time());
        }else {
            holder.textViewTimings.setVisibility(View.GONE);
        }
        holder.textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getPhone().contains(",")) {
                    String[] phoneNumbersList = model.getPhone().split(",");
                    showNumberChooserDialog(phoneNumbersList);
                } else {
                    Utils.initCall(model.getPhone(), mContext);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSparePartArrayList.size();
    }


    public class ListItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAddress;
        TextView textViewTimings;
        TextView textViewPhone;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            textViewAddress = (TextView) itemView.findViewById(R.id.textview_address);
            textViewTimings = (TextView) itemView.findViewById(R.id.textview_timing);
            textViewPhone = (TextView) itemView.findViewById(R.id.textview_phone1);
        }
    }


    private void showNumberChooserDialog(String[] phoneNumbersList) {


        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setIcon(R.mipmap.ic_launcher);
        builderSingle.setTitle("Select Phone Number");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < phoneNumbersList.length; i++) {
            arrayAdapter.add(phoneNumbersList[i]);
        }

        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                Utils.initCall(strName, mContext);
            }
        });
        builderSingle.show();

    }
}
