package com.nepo.fevercat.common.utils.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;

/**
 * @author Yoyun
 * @name ScreenShotUtil
 * @time 2017/4/6 11:20
 */

public class ScreenShotUtil {
    public static final String path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/ScreenShot/";

    // Fragment
    public static Observable<String> screenShot(final Fragment activity) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                final GlobalScreenShot screenshot = new GlobalScreenShot(activity.getActivity());
                Bitmap bitmap = screenshot.takeScreenshot(activity.getView(), new Runnable() {
                    @Override
                    public void run() {

                    }
                }, true, true);
                if (!FileUtils.isFileExist(path)) {
                    FileUtils.createFolder(path, true);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                File screenShotFile = new File(path
                        + File.separator
                        + sdf.format(new Date(System.currentTimeMillis()))
                        + ".png");
                boolean compress = false;
                try {
                    FileOutputStream fos = new FileOutputStream(screenShotFile);
                    compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    if (compress) {
                        subscriber.onNext(screenShotFile.getAbsolutePath());
                    }
                    subscriber.onCompleted();
                }

            }
        });
    }

    // Activity
    public static Observable<String> screenShot(final Activity activity, final View view) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                final GlobalScreenShot screenshot = new GlobalScreenShot(activity);
//                Bitmap bitmap = screenshot.takeScreenshot(activity.getWindow().getDecorView(), new Runnable() {
                Bitmap bitmap = screenshot.takeScreenshot(view, new Runnable() {
                    @Override
                    public void run() {

                    }
                }, true, true);
                if (!FileUtils.isFileExist(path)) {
                    FileUtils.createFolder(path, true);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                File screenShotFile = new File(path
                        + File.separator
                        + sdf.format(new Date(System.currentTimeMillis()))
                        + ".png");
                boolean compress = false;
                try {
                    FileOutputStream fos = new FileOutputStream(screenShotFile);
                    compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                } finally {
                    if (compress) {
                        subscriber.onNext(screenShotFile.getAbsolutePath());
                        // 最后通知图库更新
                        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + screenShotFile.getAbsolutePath())));
                    }
                    subscriber.onCompleted();
                }

            }
        });
    }


}
