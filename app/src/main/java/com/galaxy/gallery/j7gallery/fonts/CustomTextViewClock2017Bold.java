package com.galaxy.gallery.j7gallery.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.galaxy.gallery.j7gallery.until.FontCache;


public class CustomTextViewClock2017Bold extends TextView {
    public CustomTextViewClock2017Bold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextViewClock2017Bold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextViewClock2017Bold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Clock2017R.ttf", context);
        setTypeface(customFont);
    }
}
