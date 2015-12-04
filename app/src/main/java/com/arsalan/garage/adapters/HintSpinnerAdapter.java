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

public class HintSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SpinnerItem> mSpinnerItems;
    private LayoutInflater mLayoutInflater;

    public HintSpinnerAdapter(Context context, ArrayList<SpinnerItem> spinnerItems) {
        mContext = context;
        this.mSpinnerItems = spinnerItems;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
    	
       return mSpinnerItems.size()-1;
    }

    @Override
    public Object getItem(int position) {
        return mSpinnerItems.get(position);
    }

//    public StatesWithCode.State getItem(int position){
//       return mStateArrayList.get(position);
//    }

    public long getItemId(int position){
       return position;
    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//    	// I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
//        // Then you can get the current item using the values array (Users array) and the current position
//        // You can NOW reference each method you has created in your bean object (User class)
//        TextView label = (TextView) mLayoutInflater.inflate(R.layout.text_view_spinner, null);
//        label.setPadding((int)UtilityMethods.convertDpToPixel(8, getContext()), (int)UtilityMethods.convertDpToPixel(0, getContext()), 0, (int)UtilityMethods.convertDpToPixel(0, getContext()));
//        label.setText(mStateArrayList.get(position).getStateName());
//
//        //Add the hint text and set its color
//        if (position == getCount()) {
//        	label.setText(mStateArrayList.get(getCount()).getStateName());
//        	//label.setText("");
//        	//label.setHint(mStateArrayList.get(getCount()).getStateName()); //"Hint to be displayed"
//        	label.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
//        	label.setTextColor(mContext.getResources().getColor(R.color.spinner_hint_color));
//        }
//
//        // And finally return your dynamic (or custom) view for each spinner item
//        return label;


        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.layout_spinner_item, parent, false);
        SpinnerItem spinnerItem = (SpinnerItem) getItem(position);
        textView.setText(spinnerItem.getTitle());
        return textView;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.layout_spinner_item, parent, false);
        SpinnerItem spinnerItem = (SpinnerItem) getItem(position);
        textView.setText(spinnerItem.getTitle());
        return textView;
    }
}