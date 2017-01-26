package com.arsalan.garage.uicomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.arsalan.garage.R;
import com.arsalan.garage.utils.Utils;

/**
 * <p/>
 * Created by: Noor  Alam on 26/01/17.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */

public class TextViewPhoneCall extends TextView implements View.OnClickListener{


    public TextViewPhoneCall(Context context) {
        super(context);
        init(null);
    }

    public TextViewPhoneCall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TextViewPhoneCall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
            String fontName = a.getString(R.styleable.CustomView_fontName);
            Typeface myTypeface = null;
            if (fontName != null) {
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            } else {
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Calibre-Regular.otf");
                setTypeface(myTypeface);
            }
            a.recycle();
        }

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Utils.initCall(getText().toString(), getContext());
    }
}
