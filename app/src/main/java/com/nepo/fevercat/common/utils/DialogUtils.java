package com.nepo.fevercat.common.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.common.widget.notify.NotificationUtil;
import com.nepo.fevercat.event.CancelDialogEvent;
import com.nepo.fevercat.ui.ContainerActivity;
import com.nepo.fevercat.ui.alert.bean.AlertHeightBean;
import com.nepo.fevercat.ui.alert.bean.AlertLowBean;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;

import org.greenrobot.eventbus.EventBus;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.common.utils
 * 文件名:  DialogUtils
 * 作者 :   <sen>
 * 时间 :  下午5:26 2017/7/20.
 * 描述 :  对话框
 */

public class DialogUtils {

    private static DialogUtils mDialogUtils;
    private Context mContext;

    private DialogUtils(Context context) {
        mContext = context;
    }

    public static DialogUtils getInstance(Context context) {
        if (mDialogUtils == null) {
            mDialogUtils = new DialogUtils(context);
        }else {
            mDialogUtils.mContext=context;
        }

        return mDialogUtils;
    }


    /**
     * 单OK对话框
     *
     * @param content
     */
    public void SingleConfirmDialog(String content) {

        new MaterialDialog.Builder(mContext)
                .title(mContext.getString(R.string.app_name))
                .content(content)
                .positiveText(mContext.getString(R.string.confirm))
                .show();

    }
    /**
     * 双选提示框 提示
     *
     * @param content
     * @param positiveClick
     * @param negativeClick
     */
    public void DoubleOptionsDialog(String content,MaterialDialog.SingleButtonCallback positiveClick, MaterialDialog.SingleButtonCallback negativeClick) {
        new MaterialDialog.Builder(mContext)
                .title(mContext.getString(R.string.follow_invite))
                .content(content)
                .positiveText(mContext.getString(R.string.confirm))
                .negativeText(mContext.getString(R.string.cancel))
                .cancelable(false)
                .onPositive(positiveClick)
                .onNegative(negativeClick)
                .show();
    }

    /**
     * 设备连接断开 提示
     *
     * @param positiveClick
     * @param negativeClick
     */
    public void blueDisconnectDialog(MaterialDialog.SingleButtonCallback positiveClick, MaterialDialog.SingleButtonCallback negativeClick) {
        new MaterialDialog.Builder(mContext)
                .title(mContext.getString(R.string.app_name))
                .content(mContext.getString(R.string.devices_disconnect_tip))
                .positiveText(mContext.getString(R.string.confirm))
                .negativeText(mContext.getString(R.string.cancel))
                .cancelable(false)
                .onPositive(positiveClick)
                .onNegative(negativeClick)
                .show();
    }


    /**
     * 高温提醒弹窗
     */
    public void AlertHeightDialog() {

        Activity activity = (Activity) mContext;
        if (activity.isFinishing()) {
            return;
        }

        AlertHeightBean alertHeightBean = AppConstant.mAlertHeightBean;
        if (alertHeightBean != null) {
            if (TextUtils.equals(alertHeightBean.getSSelectType(), "0")) {
                // 低热
                // 播放选中音频
                AudioPlayerUtils.getInstance(mContext).playRaw(R.raw.low, true, true);
            } else if (TextUtils.equals(alertHeightBean.getSSelectType(), "1")) {
                // 中
                // 播放选中音频
                AudioPlayerUtils.getInstance(mContext).playRaw(R.raw.med, true, true);
            } else if (TextUtils.equals(alertHeightBean.getSSelectType(), "2")) {
                // 高
                // 播放选中音频
                AudioPlayerUtils.getInstance(mContext).playRaw(R.raw.high, true, true);
            }
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alert_height_dialog, null);
        TextView dialog_bb_tip = (TextView) view.findViewById(R.id.tv_alert_height_dialog_bb_tip);
        TextView dialog_temp_tip = (TextView) view.findViewById(R.id.tv_alert_height_dialog_temp_tip);
        RelativeLayout dialog_cancel = (RelativeLayout) view.findViewById(R.id.rl_alert_height_dialog_cancel);
        RelativeLayout dialog_confirm = (RelativeLayout) view.findViewById(R.id.rl_alert_height_dialog_confirm);

        String notifyStr = "";

        BabyInfosBean mCurrentBabyInfo = AppConstant.mCurrentBabyInfo;
        if (mCurrentBabyInfo != null) {
            String stringFormat = mContext.getString(R.string.alert_height_dialog_temp_tip_one);
            dialog_bb_tip.setText(String.format(stringFormat, mCurrentBabyInfo.getNickname()));
            notifyStr = dialog_bb_tip.getText().toString().trim();
        }

//        if (!TextUtils.isEmpty(AppConstant.mCurrentAlertHeightTemp)) {
//            dialog_temp_tip.setText(ConstantUtils.IsCelsiusUnit(AppConstant.mCurrentAlertHeightTemp));
//        }

        if (alertHeightBean != null) {
            if (TextUtils.equals(alertHeightBean.getSSelectType(), "0")) {
                dialog_temp_tip.setText(Utils.getContext().getString(R.string.temp_status_low));
            } else if (TextUtils.equals(alertHeightBean.getSSelectType(), "1")) {
                dialog_temp_tip.setText(Utils.getContext().getString(R.string.temp_status_middle));
            } else if (TextUtils.equals(alertHeightBean.getSSelectType(), "2")) {
                dialog_temp_tip.setText(Utils.getContext().getString(R.string.temp_status_hot));
            }
            notifyStr += dialog_temp_tip.getText().toString().trim();

        }


        final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .customView(view, false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show();


        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog.show();
        }

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AudioPlayerUtils.sIsRecordStopMusic) {
                    // 停止播放
                    AudioPlayerUtils.getInstance(mContext).stop();
                    EventBus.getDefault().post(new CancelDialogEvent());
                }
                dialog.dismiss();
            }
        });
        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AudioPlayerUtils.sIsRecordStopMusic) {
                    // 停止播放
                    AudioPlayerUtils.getInstance(mContext).stop();
                }
                dialog.dismiss();
            }
        });

        // 通知
        NotificationUtil notificationUtil = new NotificationUtil(activity);
        notificationUtil.setNotfiy(R.mipmap.fever_cat_logo, activity.getString(R.string.app_name),
                activity.getString(R.string.alert_nav_height_temp),
                notifyStr,
                ContainerActivity.class, 1);

    }


    /**
     * 降温用药提醒
     */
    public void AlertMedicineDialog(AlertLowBean alertLowBean) {

        Activity activity = (Activity) mContext;
        if (activity.isFinishing()) {
            return;
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alert_medicine_dialog, null);
        TextView dialog_time_tip = (TextView) view.findViewById(R.id.tv_alert_medicine_dialog_time_tip);
        TextView dialog_name_tip = (TextView) view.findViewById(R.id.tv_alert_medicine_dialog_name_tip);
        RelativeLayout dialog_cancel = (RelativeLayout) view.findViewById(R.id.rl_alert_height_dialog_cancel);
        RelativeLayout dialog_confirm = (RelativeLayout) view.findViewById(R.id.rl_alert_height_dialog_confirm);

        dialog_time_tip.setText(alertLowBean.getMTime());
        dialog_name_tip.setText(alertLowBean.getMedicineName());

        final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .customView(view, false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show();


        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog.show();
        }

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CancelDialogEvent());
                dialog.dismiss();
            }
        });
        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 通知内容
        String tipStr = dialog_time_tip.getText().toString() + "\t" + dialog_name_tip.getText().toString();

        // 通知
        NotificationUtil notificationUtil = new NotificationUtil(activity);
        notificationUtil.setNotfiy(R.mipmap.fever_cat_logo,
                activity.getString(R.string.app_name),
                activity.getString(R.string.alert_medicine_dialog_title),
                tipStr,
                ContainerActivity.class, 1);


    }

    /**
     * 降温用药提醒
     */
    public void AlertWaterDialog(AlertLowBean alertLowBean) {

        Activity activity = (Activity) mContext;
        if (activity.isFinishing()) {
            return;
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.view_alert_water_dialog, null);
        TextView dialog_unit_tip = (TextView) view.findViewById(R.id.tv_alert_water_dialog_time_tip);
        TextView dialog_time_tip = (TextView) view.findViewById(R.id.tv_alert_water_dialog_name_tip);
        RelativeLayout dialog_cancel = (RelativeLayout) view.findViewById(R.id.rl_alert_height_dialog_cancel);
        RelativeLayout dialog_confirm = (RelativeLayout) view.findViewById(R.id.rl_alert_height_dialog_confirm);

        String strWaterUnit = mContext.getString(R.string.alert_water_unit_s);
        String strUnit = mContext.getString(R.string.alert_water_time_interval_s);
        dialog_unit_tip.setText(String.format(strWaterUnit, alertLowBean.getMWaterUnits()));
        dialog_time_tip.setText(String.format(strUnit, alertLowBean.getMWaterBeginTime(), alertLowBean.getMWaterEndTime()));

        final MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                .customView(view, false)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .show();


        if (dialog.isShowing()) {
            dialog.dismiss();
            dialog.show();
        }

        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CancelDialogEvent());
                dialog.dismiss();
            }
        });
        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 通知提醒
        String tipStr = dialog_unit_tip.getText().toString() + dialog_time_tip.getText().toString();

        // 通知
        NotificationUtil notificationUtil = new NotificationUtil(activity);
        notificationUtil.setNotfiy(R.mipmap.fever_cat_logo,
                activity.getString(R.string.app_name),
                activity.getString(R.string.alert_water_dialog_title),
                tipStr,
                ContainerActivity.class, 1);

    }

    /**
     * 是否要发送信息给亲友提醒
     */
    public void SendSmsToFollowDialog() {
        Activity activity = (Activity) mContext;
        if (activity.isFinishing()) {
            return;
        }
        final AlertHeightBean alertHeightBean = AppConstant.mAlertHeightBean;
        new MaterialDialog.Builder(mContext)
                .content(mContext.getString(R.string.send_follow_sms))
                .positiveText(mContext.getString(R.string.send))
                .negativeText(mContext.getString(R.string.no_send))
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SendSmsUtils.getInstance(mContext).SendSmsToFollow(alertHeightBean.getSTemp());

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();

    }



}
