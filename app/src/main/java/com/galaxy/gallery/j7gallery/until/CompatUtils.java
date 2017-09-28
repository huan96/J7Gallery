package com.galaxy.gallery.j7gallery.until;

/**
 * Created by huand on 9/20/2017.
 */

import android.content.Context;

public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}