package com.nepo.fevercat.common.utils.screenshot;

import android.graphics.Bitmap;
import android.view.View;

public class SurfaceControl {

    public static Bitmap screenshot(View view) {
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap bmp = view.getDrawingCache();
        return shot(view);
    }

    public static Bitmap shot(View v) {
        v.clearFocus(); //

        v.setPressed(false); //

        // 能画缓存就返回false

        boolean willNotCache = v.willNotCacheDrawing();

        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();

        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {

            v.destroyDrawingCache();

        }

        v.buildDrawingCache();

        Bitmap cacheBitmap = v.getDrawingCache();

        if (cacheBitmap == null) {

            return null;

        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view

        v.destroyDrawingCache();

        v.setWillNotCacheDrawing(willNotCache);

        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }
}
