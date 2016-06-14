package com.arsalan.garage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.models.ShareOptionItem;

import java.util.ArrayList;

/**
 * <p/>
 * Created by: Noor  Alam on 05/04/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class ShareListAdapter extends BaseAdapter {
    private ArrayList<ShareOptionItem> mSpinnerItems;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ShareListAdapter(Context context, ArrayList<ShareOptionItem> spinnerItems) {
        this.mSpinnerItems = spinnerItems;
        this.mContext = context;
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
        ShareOptionItem shareOptionItem = mSpinnerItems.get(position);
        View rooView = mLayoutInflater.inflate(R.layout.list_item_share_option, parent, false);
        ImageView imageViewShareIcon = (ImageView) rooView.findViewById(R.id.imageview_icon);
        imageViewShareIcon.setImageResource(shareOptionItem.getImageIcon());
        TextView textViewTitle = (TextView) rooView.findViewById(R.id.textview_title);
        textViewTitle.setText(mContext.getString(shareOptionItem.getTitle()));
        return rooView;
    }
}
