package com.nepo.fevercat.common.widget.notify;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * 判断App是否处于前台
 */
public class AppStateUtil {

    private static String PAGETAG = "XAppState" ;
    public static boolean appIsForeGround = false;

    private static int appCount = 0;

    public enum AppState {
        RUNNING, FOREGROUND
    }

    /**
     * @param application
     */
    public static void init(Application application){

        /**
         * 通过ActivityLifecycleCallbacks来批量统计Activity的生命周期，来做判断，此方法在API 14以上均有效，但是需要在Application中注册此回调接口
         * 必须：
         * 1. 自定义Application并且注册ActivityLifecycleCallbacks接口
         * 2. AndroidManifest.xml中更改默认的Application为自定义
         * 3. 当Application因为内存不足而被Kill掉时，这个方法仍然能正常使用。虽然全局变量的值会因此丢失，但是再次进入App时候会重新统计一次的
         */
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount ++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            // 折冲樽俎 不出樽俎之间 而折冲千里之外
            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount --;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 当前App是否在前台
     * @return true：是，false:不是。
     */
    public static boolean isAppIsForeGround(){

        Log.d("PAGETAG","Activity appCount = " + appCount);

        return appCount > 0;

    }
}
