package com.arsalan.garage.uicomponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.arsalan.garage.R;


public class CustomTextViewEnglish extends TextView {
    public CustomTextViewEnglish(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomTextViewEnglish(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextViewEnglish(Context context) {
        super(context);
        init(null);
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
    }
}