package com.arsalan.garage.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arsalan.garage.R;


/**
 * <p/>
 * Created by: Noor  Alam on 12/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class CustomDialogHelper {

    private Typeface mTypefaceDialogTitle;
    private Resources mResources;
    //private Context mContext;
    private Typeface mTypefaceDialogButton;
    private int mFontSizeTitle;
    private int mDividerColor;

    public CustomDialogHelper(Context context) {
        initDefault(context);

    }

    private void initDefault(Context context){
        mResources = context.getResources();
        mTypefaceDialogButton = Typeface.createFromAsset(mResources.getAssets(),"fonts/"+mResources.getString(R.string.font_calibre_medium));
        mFontSizeTitle = 17;
        mTypefaceDialogTitle = Typeface.createFromAsset(mResources.getAssets(),"fonts/"+mResources.getString(R.string.font_calibre_bold));
        mDividerColor = android.R.color.black;
    }

    public void changeDialog(final AlertDialog dialog) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                changeDialogButtonStyle(dialog.getButton(AlertDialog.BUTTON_NEGATIVE));
                changeDialogButtonStyle(dialog.getButton(AlertDialog.BUTTON_NEUTRAL));
                changeDialogButtonStyle(dialog.getButton(AlertDialog.BUTTON_POSITIVE));
                changeDialogTitleStyle(dialog);
                changeTitleDividerColor(dialog);
            }
        });
    }

    private void changeDialogButtonStyle(Button button) {
        if (button == null){
            return;
        }
        button.setTextColor(mResources.getColor(android.R.color.black));
        button.setTypeface(mTypefaceDialogButton);
    }

    private void changeDialogTitleStyle(Dialog dialog) {
        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView mTextViewDialogTitle = (TextView) dialog.findViewById(textViewId);
        if (mTextViewDialogTitle == null){
            return;
        }
        mTextViewDialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, mFontSizeTitle);
        mTextViewDialogTitle.setTypeface(mTypefaceDialogTitle);
        mTextViewDialogTitle.setTextColor(mResources.getColor(android.R.color.black));
    }

    public void setTitleTextSize(Dialog dialog, int fontSize){
        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView textViewDialogTitle = (TextView) dialog.findViewById(textViewId);
        if (textViewDialogTitle == null){
            return;
        }
        textViewDialogTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    private void changeTitleDividerColor(Dialog dialog){
        int titleDividerId = dialog.getContext().getResources().getIdentifier("titleDivider", "id", "android");
        View titleDivider = dialog.getWindow().getDecorView().findViewById(titleDividerId);
        titleDivider.setBackgroundColor(mDividerColor);
    }
}
