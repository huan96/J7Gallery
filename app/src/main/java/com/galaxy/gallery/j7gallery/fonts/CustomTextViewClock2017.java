package com.galaxy.gallery.j7gallery.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.galaxy.gallery.j7gallery.until.FontCache;


public class CustomTextViewClock2017 extends TextView {
    public CustomTextViewClock2017(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextViewClock2017(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextViewClock2017(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("Clock2017L.ttf", context);
        setTypeface(customFont);
    }
}
