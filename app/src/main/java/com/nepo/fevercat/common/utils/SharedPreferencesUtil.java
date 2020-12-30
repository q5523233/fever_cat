package com.nepo.fevercat.common.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  SharedPreferencesUtil
 * 作者 :   <sen>
 * 时间 :  上午11:16 2017/2/15.
 * 描述 :  SharedPreferences 本地缓存工具类
 */

public class SharedPreferencesUtil {

    private static Gson mGson;
    private static SharedPreferences mPreferences;

    public static void init(Application application, Gson gson) {
        mGson = gson;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(application);
    }


    public static String getString(String key,
                                   final String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }

    public static void setString(final String key,
                                 final String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(Context context, final String key,
                                     final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(final String key) {
        return mPreferences.contains(
                key);
    }

    public static void setBoolean(Context context, final String key,
                                  final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).apply();
    }

    public static void setInt(Context context, final String key,
                              final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, final String key,
                             final int defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setFloat(Context context, final String key,
                                final float value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).apply();
    }

    public static float getFloat(Context context, final String key,
                                 final float defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setLong(Context context, final String key,
                               final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, final String key,
                               final long defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }

    public static void clear() {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void removeAtTag(String tag){
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove(tag);
        editor.apply();
    }



    public static boolean set(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static void set(String key, Object obj) {
        try {
            if (obj == null) {
                String e = mGson.toJson("");
                set(key, e);
            }else{
                String e = mGson.toJson(obj);
                set(key, e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <T> T getObject(String name, Class<T> clazz) {
        T obj = null;

        try {
            String e = getString(name,null);
            if (TextUtils.isEmpty(e)||TextUtils.equals(e,"\"\"")) {
                return obj;
            }
            obj = mGson.fromJson(e, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

}
