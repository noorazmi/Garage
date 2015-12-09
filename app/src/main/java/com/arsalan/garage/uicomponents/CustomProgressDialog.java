package com.arsalan.garage.uicomponents;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.arsalan.garage.R;


/**
 * Created by Ankit on 16/10/15.
 */
public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);

    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
    }

    @Override
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.layout_progress_dialog);
    }
}
