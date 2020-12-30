package com.nepo.fevercat.common.utils;

import android.widget.Toast;

/**
 * Created by gh0st on 2017/1/17.
 */

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(int id) {
        if (mToast == null) {
            mToast = Toast.makeText(Utils.getContext(), id, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(id);
        }
        mToast.show();
    }

    public static void showToast(String string) {
        if (mToast == null) {
            mToast = Toast.makeText(Utils.getContext(), string, Toast.LENGTH_LONG);
        } else {
            mToast.setText(string);
        }
        mToast.show();
    }
}
