package com.gutefind.mobile.util;


import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * <a href="https://stackoverflow.com/questions/4605527/converting-pixels-to-dp/9563438">StackOverflow Converting pixels to dp</a>
 */

public abstract class DisplayUtil {

    public static float convertDipToPixels(float dip) {
        return convertDipToPixels(dip, Resources.getSystem().getDisplayMetrics());
    }

    public static float convertDipToPixels(float dip, Context context) {
        return convertDipToPixels(dip, context.getResources().getDisplayMetrics());
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dip A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDipToPixels(float dip, DisplayMetrics displayMetrics) {
        return dip * ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param pixels  A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDip(float pixels, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return pixels / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
