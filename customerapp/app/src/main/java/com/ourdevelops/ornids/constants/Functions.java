package com.ourdevelops.ornids.constants;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import java.util.Objects;

/**
 * Created by Ourdevelops Team on 12/20/2019.
 */

public class Functions {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}