package com.nepo.fevercat.common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresPermission;
import android.support.v13.app.ActivityCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.app.BaseApplication;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;

import java.util.List;

import static android.Manifest.permission.SEND_SMS;

/**
 * Created by shenmai8 on 2018/4/19.
 */

public class SendSmsUtils {
    private static SendSmsUtils mSmsUtils;
    private Context mContext;

    private SendSmsUtils(Context context) {
        mContext = context;
    }

    public static SendSmsUtils getInstance(Context context) {
        if (mSmsUtils == null) {
            mSmsUtils = new SendSmsUtils(context);
        }

        return mSmsUtils;
    }

    /**
     * 发送即时体温给关注的亲友
     *
     * @param temp
     */

    public void SendSmsToFollow(String temp) {

        //        Uri uri  = Uri.parse("smsto:10010;10000;10086");
        //        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        //        intent.putExtra("sms_body","发烧猫：您所关注的"
        //                + AppConstant.mCurrentBabyInfo.getNickname()
        //                +"正处于发热状态，当前体温为"
        //                +temp
        //                +"敬请留意孩子体温状况");
        //        mContext.startActivity(intent);
        //获取亲友信息
        FollowListResBean followListResBean = SharedPreferencesUtil.getObject(AppConstant.kEY_FREIEND, FollowListResBean.class);
        if (followListResBean == null || followListResBean.getBindUsers() == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.SEND_SMS}, 0);
            return;
        }
        String content = "发烧猫：您所关注的"
//                + AppConstant.mCurrentBabyInfo.getNickname()
                + "maomao"
                + "正处于发热状态，当前体温为"
                + temp
                + "敬请留意孩子体温状况";
        for (FollowListResBean.BindUsersBean usersBean : followListResBean.getBindUsers()) {
            sendSmsSilent(usersBean.getBindUserName(),content);
        }

    }

    @RequiresPermission(SEND_SMS)
    public void sendSmsSilent(final String phoneNumber, final String content) {
        if (TextUtils.isEmpty(content))
            return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(BaseApplication.getAppContext(), 0, new Intent("send"), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }
}
