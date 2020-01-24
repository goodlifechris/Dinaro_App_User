package com.dinaro.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.google.android.material.textfield.TextInputEditText;
import android.util.AttributeSet;

import com.dinaro.R;

public class MyTextInputEditText extends TextInputEditText {

    //private TypeFactory mFontFactory;

    public MyTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public MyTextInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context, attrs);
    }

    public MyTextInputEditText(Context context) {
        super(context);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyTextView,
                0, 0);
        int typefaceType;
        try {
          //  typefaceType = array.getInteger(R.styleable.MyTextView_font_name, 0);
        } finally {
            array.recycle();
        }
        if (!isInEditMode()) {
           // setTypeface(getTypeFace(typefaceType));
        }

    }

   /* private Typeface getTypeFace(int type) {
        if (mFontFactory == null)
            mFontFactory = newback TypeFactory(getContext());

        switch (type) {
            case ViewConstants.REGULAR:
                return mFontFactory.regular;
            case ViewConstants.BOLD:
                return mFontFactory.bold;
            case ViewConstants.LIGHT:
                return mFontFactory.light;
            case ViewConstants.ITALIC:
                return mFontFactory.italic;
            default:
                return mFontFactory.regular;
        }
    }*/
}
