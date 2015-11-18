package com.github.flinbor.pushclint.util;
/**
 * Created by Bogdanov Oleksandr on 17,October,2015
 */

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * converter of dimensions
 */
public class UIHelper {

    private UIHelper() {
    }

    /**
     * @param dp independent pixel
     * @return int pixel
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * @param px pixel
     * @return independent pixel
     */
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * hide keyboard
     *
     * @param activity
     * @param view
     */
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
