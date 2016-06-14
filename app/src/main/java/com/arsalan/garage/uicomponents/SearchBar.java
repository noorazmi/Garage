package com.arsalan.garage.uicomponents;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.arsalan.garage.R;

/**
 * <p/>
 * Created by: Noor  Alam on 15/05/16.<br/>
 * Email id: noor.alam@tothenew.com<br/>
 * Skype id: mfsi_noora
 * <p/>
 */
public class SearchBar extends FrameLayout {

    private EditText editTextSearch;
    private TextWatcher textWatcher;

    public SearchBar(Context context) {
        super(context);
        init();
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_search, this);
    }

}
