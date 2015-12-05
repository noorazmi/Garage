package com.arsalan.garage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.models.SpinnerItem;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 28/11/15.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CustomSpinnerAdapter extends BaseAdapter {
    private ArrayList<SpinnerItem> mSpinnerItems;
    private LayoutInflater mLayoutInflater;

    public CustomSpinnerAdapter(Context context, ArrayList<SpinnerItem> spinnerItems) {
        this.mSpinnerItems = spinnerItems;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mSpinnerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mSpinnerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.layout_spinner_item_arb, parent, false);
        SpinnerItem spinnerItem = (SpinnerItem) getItem(position);
        textView.setText(spinnerItem.getTitle());
        return textView;
    }

}
