package com.nepo.fevercat.ui.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chingteach.chartlibrary.gesture.ZoomType;
import com.chingteach.chartlibrary.model.Line;
import com.chingteach.chartlibrary.model.LineChartData;
import com.chingteach.chartlibrary.model.PointValue;
import com.chingteach.chartlibrary.model.ValueShape;
import com.chingteach.chartlibrary.model.Viewport;
import com.chingteach.chartlibrary.view.LineChartView;
import com.nepo.fevercat.R;
import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.BaseActivity;
import com.nepo.fevercat.common.utils.ConstantUtils;
import com.nepo.fevercat.common.utils.ToastUtils;
import com.nepo.fevercat.common.utils.screenshot.ScreenShotUtil;
import com.nepo.fevercat.common.widget.circular.CircularImage;
import com.nepo.fevercat.ui.data.bean.ShareDataBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.data.bean.TempOneDayDataResBean;
import com.nepo.fevercat.ui.data.contract.TempHistoryListContract;
import com.nepo.fevercat.ui.data.model.TempHistoryListModel;
import com.nepo.fevercat.ui.data.presenter.TempHistoryListPresenter;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data
 * 文件名:  ShareDataActivity
 * 作者 :   <sen>
 * 时间 :  下午5:20 2017/7/13.
 * 描述 :  分享
 */

public class ShareDataActivity extends BaseActivity<TempHistoryListPresenter, TempHistoryListModel> implements TempHistoryListContract.View {


    @BindView(R.id.ll_share_contain)
    LinearLayout llShareContain;
    @BindView(R.id.rl_share_control_qq_channel)
    RelativeLayout rlShareControlQqChannel;
    @BindView(R.id.rl_share_control_wx_channel)
    RelativeLayout rlShareControlWxChannel;
    @BindView(R.id.rl_share_control_wb_channel)
    RelativeLayout rlShareControlWbChannel;
    @BindView(R.id.iv_mine_user_pic)
    CircularImage ivMineUserPic;
    @BindView(R.id.tv_mine_user_name)
    TextView tvMineUserName;
    @BindView(R.id.tv_share_height_temp)
    TextView tvShareHeightTemp;
    @BindView(R.id.tv_share_height_hot)
    TextView tvShareHeightHot;
    @BindView(R.id.tv_share_time)
    TextView tvShareTime;
    @BindView(R.id.view_share_hello_line_chart)
    LineChartView viewShareHelloLineChart;
    @BindView(R.id.rl_share_chart)
    RelativeLayout rlShareChart;
    @BindView(R.id.ll_share_no_data)
    LinearLayout llShareNoData;
    @BindView(R.id.tv_share_alert_time)
    TextView tvShareAlertTime;


    private ShareDataBean mShareDataBean;


    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ShareDataActivity.class);
        return intent;
    }


    @Override
    public int getLayoutId() {

        return R.layout.act_share_data;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mShareDataBean = AppConstant.mShareDataBean;
        viewShareHelloLineChart.setZoomType(ZoomType.HORIZONTAL);
        viewShareHelloLineChart.setViewportCalculationEnabled(true);
        viewShareHelloLineChart.setInteractive(false);
        setUserInfo();
        setHeightTempInfo();
        setChartInfo();

    }

    @OnClick(R.id.rl_share_bar_back)
    public void onBackClick() {
        finish();
    }


    /**
     * 宝宝信息
     */
    private void setUserInfo() {
        BabyInfosBean babyInfosBean = mShareDataBean.getBabyInfosBean();
        if (babyInfosBean != null) {
            ConstantUtils.loadLoginUserImg(babyInfosBean.getHeadImageUrl(), ivMineUserPic);
            tvMineUserName.setText(babyInfosBean.getNickname());
        }
    }

    /**
     * 最高温
     */
    private void setHeightTempInfo() {
        TempHistoryDataResBean tempHistoryDataResBean = mShareDataBean.getTempHistoryDataResBean();
        if (tempHistoryDataResBean != null) {
            TempHistoryDataResBean.RecordBean recordBean = tempHistoryDataResBean.getRecord().get(0);
            if (recordBean != null) {
                tvShareHeightTemp.setText(recordBean.getHightTemperature());
                tvShareHeightHot.setText(recordBean.getFeverDay());
                tvShareTime.setText(mShareDataBean.getTime());
            }

        }

        if (AppConstant.mCurrentSuperHotTime==0l) {
            AppConstant.mCurrentSuperHotTime = System.currentTimeMillis();
        }
        long reduceTime = System.currentTimeMillis() - AppConstant.mCurrentSuperHotTime;
        String sTimeStr = ConstantUtils.formatTimeByMillisecond(reduceTime);
        tvShareAlertTime.setText(sTimeStr);

    }

    /**
     * 图表信息
     */
    private void setChartInfo() {
        TempOneDayDataResBean tempOneDayDataResBean = mShareDataBean.getTempOneDayDataResBean();
        if (tempOneDayDataResBean != null) {
            List<TemperaturesBean> temperatures = tempOneDayDataResBean.getTemperatures();
            if (temperatures.size() > 0) {
                rlShareChart.setVisibility(View.VISIBLE);
                llShareNoData.setVisibility(View.GONE);
                initOneDaLineChartData(temperatures);
            } else {
                rlShareChart.setVisibility(View.GONE);
                llShareNoData.setVisibility(View.VISIBLE);
            }
        } else {
            rlShareChart.setVisibility(View.GONE);
            llShareNoData.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 图表
     *
     * @param tempOneDayDataResBean
     */
    private void initOneDaLineChartData(List<TemperaturesBean> tempOneDayDataResBean) {
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < tempOneDayDataResBean.size(); i++) {
            TemperaturesBean temperature = tempOneDayDataResBean.get(i);
            Float aFloat = Float.valueOf(temperature.getTemperature());
            Log.e("tag", "initOneDaLineChartData: "+aFloat );
            if (aFloat > 42f) {
                aFloat = 42f;
            }
            if (aFloat < 35f) {
                aFloat = 35f;
            }
            if (TextUtils.equals(temperature.getTemperature(), "0.0")) {
                aFloat = 35f;
            }
            values.add(new PointValue(i, aFloat));
        }
        Line line = new Line(values);
        line.setColor(Color.parseColor("#84b9ff"));
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);
        line.setFilled(false);
        line.setStrokeWidth(2);
        line.setHasLabels(false);
        line.setHasLabelsOnlyForSelected(false);
        line.setHasLines(true);
        line.setHasPoints(false);
        line.setAreaTransparency(20);
        lines.add(line);

        LineChartData lineChartData = new LineChartData(lines);
        lineChartData.setAxisXBottom(null);
        lineChartData.setAxisYLeft(null);
        lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);
        viewShareHelloLineChart.setLineChartData(lineChartData);

        // x、y最小值 最大值
        final Viewport maxV = new Viewport(viewShareHelloLineChart.getMaximumViewport());
        maxV.bottom = 32.8f;
        maxV.top = 42.6f;
        maxV.left = 0;
        maxV.right = values.size();
        viewShareHelloLineChart.setMaximumViewport(maxV);


        Viewport v = new Viewport(0, 42.6f, 6, 32.8f);
        viewShareHelloLineChart.setCurrentViewport(maxV);

    }


    /**
     * QQ分享
     */
    @OnClick(R.id.rl_share_control_qq_channel)
    public void onShareQQClick() {

        if (isAppInstalled("com.tencent.mobileqq")) {
            ScreenShotUtil.screenShot(this, llShareContain)
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            UMImage umImage = new UMImage(mContext, new File(s));
                            new ShareAction(ShareDataActivity.this)
                                    .withMedia(umImage)//分享图片
                                    .setPlatform(SHARE_MEDIA.QQ)//传入平台
                                    .setCallback(mUMShareListener)//回调监听器
                                    .share();


                        }
                    });
        } else {
            ToastUtils.showToast("请先安装QQ");
        }


    }

    /**
     * 微信分享
     */
    @OnClick(R.id.rl_share_control_wx_channel)
    public void onShareWXClick() {

        if (isAppInstalled("com.tencent.mm")) {
            ScreenShotUtil.screenShot(this, llShareContain)
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
//                            UMImage umImage = new UMImage(mContext, new File(s));
//                            new ShareAction(ShareDataActivity.this)
//                                    .withMedia(umImage)//分享图片
//                                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
//                                    .setCallback(mUMShareListener)//回调监听器
//                                    .share();
                            shareToWX(s);
                        }
                    });
        } else {
            ToastUtils.showToast("请先安装微信App");
        }


    }

    /**
     * 微博分享
     */
    @OnClick(R.id.rl_share_control_wb_channel)
    public void onShareWBClick() {

        // com.sina.weibo 是否安装微博
        if (isAppInstalled("com.sina.weibo")) {
            ScreenShotUtil.screenShot(this, llShareContain)
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            UMImage umImage = new UMImage(mContext, new File(s));
                            new ShareAction(ShareDataActivity.this)
                                    .withMedia(umImage)//分享图片
                                    .setPlatform(SHARE_MEDIA.SINA)//传入平台
                                    .setCallback(mUMShareListener)//回调监听器
                                    .share();
                        }
                    });
        } else {
            ToastUtils.showToast("请先安装微博App");
        }


    }


    /**
     * 分享微信
     *
     * @param url
     */
    private void shareToWX(String url) {
        String WxPackageStr = "com.tencent.mm";
        String WxTimeLinePackageStr = "com.tencent.mm.ui.tools.ShareImgUI";
        String WxType = "image/*";
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(WxPackageStr, WxTimeLinePackageStr);
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(WxType);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(url)));
        startActivity(intent);

    }


    private void shareToWXByUM(String url){
        UMImage umImage = new UMImage(mContext, new File(url));
        new ShareAction(ShareDataActivity.this)
                .withMedia(umImage)//分享图片
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .setCallback(mUMShareListener)//回调监听器
                .share();
    }


    UMShareListener mUMShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.d("--分享", "onStart");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Log.d("--分享", "onResult");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.d("--分享", "onError");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.d("--分享", "onCancel");
        }
    };


    /**
     * 监测是否安装应用
     */
    private boolean isAppInstalled(String packageStr) {
        PackageInfo packageInfo;
        try {
            packageInfo = getPackageManager().getPackageInfo(packageStr, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void returnHistoryListInfo(TempHistoryDataResBean tempHistoryDataResBean) {

    }

    @Override
    public void returnBBInfo(MineBBResBean mineBBResBean) {

    }

    @Override
    public void returnBBOneDayInfo(TempOneDayDataResBean baseResBean) {

    }

    @Override
    public void returnErrInfo(String errMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
