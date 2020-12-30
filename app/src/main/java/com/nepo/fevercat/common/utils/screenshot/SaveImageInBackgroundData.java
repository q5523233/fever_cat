package com.nepo.fevercat.common.utils.screenshot;

/**
 * @author Yoyun
 * @name SaveImageInBackgroundData
 * @time 2017/4/6 11:13
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * POD used in the AsyncTask which saves an image in the background.
 */
class SaveImageInBackgroundData {
    Context context;
    Bitmap image;
    Uri imageUri;
    Runnable finisher;
    int iconSize;
    int result;

    void clearImage() {
        image = null;
        imageUri = null;
        iconSize = 0;
    }

    void clearContext() {
        context = null;
    }
}