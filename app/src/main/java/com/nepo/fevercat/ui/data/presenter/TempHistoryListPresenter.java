package com.nepo.fevercat.ui.data.presenter;

import com.nepo.fevercat.app.AppConstant;
import com.nepo.fevercat.base.rx.RxSubscriber;
import com.nepo.fevercat.common.utils.DateUtil;
import com.nepo.fevercat.common.utils.GreenDaoUtils;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataReqBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.data.bean.TempOneDayDataResBean;
import com.nepo.fevercat.ui.data.contract.TempHistoryListContract;
import com.nepo.fevercat.ui.mine.bean.BabyInfosBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.real.bean.TemperaturesBean;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * 项目名: FeverCat
 * 包名 :  com.nepo.fevercat.ui.data.presenter
 * 文件名:  TempHistoryListPresenter
 * 作者 :   <sen>
 * 时间 :  上午9:46 2017/7/4.
 * 描述 :
 */

public class TempHistoryListPresenter extends TempHistoryListContract.Presenter {


    @Override
    public void getHistoryListInfoRequest(final TempHistoryDataReqBean tempHistoryDataReqBean, boolean isShow) {
        if (AppConstant.IS_OFFLINEMODE) {
            GreenDaoUtils.getInstance(mContext).getTempListInfo(tempHistoryDataReqBean.localId).flatMap(new Func1<List<TemperaturesBean>, Observable<TempHistoryDataResBean>>() {
                @Override
                public Observable<TempHistoryDataResBean> call(List<TemperaturesBean> temperaturesBeen) {
                    final TempHistoryDataResBean tempHistoryDataResBean = new TempHistoryDataResBean();
                    if (temperaturesBeen.size() > 0) {
                        tempHistoryDataResBean.setCode("0");
                    }
                    for (TemperaturesBean temperaturesBean : temperaturesBeen) {
                        int[] out = new int[3];
                        DateUtil.getDateSplit(temperaturesBean.getTempTime(), out);
                        TempHistoryDataResBean.RecordBean recordBean = tempHistoryDataResBean.containsYearData(out[0]);
                        TempHistoryDataResBean.RecordBean.MonthBean monthBean = recordBean.containsMonthData(out[1], temperaturesBean, recordBean);
                        TempHistoryDataResBean.RecordBean.MonthBean.RecordDetailsBean m = monthBean.containsRecordDetailsData(temperaturesBean, out[2]);
                    }
                    return Observable.create(new Observable.OnSubscribe<TempHistoryDataResBean>() {
                        @Override
                        public void call(Subscriber<? super TempHistoryDataResBean> subscriber) {
                            subscriber.onNext(tempHistoryDataResBean);
                        }
                    });
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TempHistoryDataResBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(TempHistoryDataResBean tempHistoryDataResBean) {
                    mView.returnHistoryListInfo(tempHistoryDataResBean);
                }
            });
            return;
        }
        mRxManage.add(mModel.getHistoryListInfo(tempHistoryDataReqBean)
                .subscribe(new RxSubscriber<TempHistoryDataResBean>(mContext, isShow) {
                    @Override
                    protected void _onNext(TempHistoryDataResBean tempHistoryDataResBean) {
                        mView.returnHistoryListInfo(tempHistoryDataResBean);
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));
    }

    @Override
    public void putMineBBInfoRequest(MineBBReqBean mineBBReqBean) {
        if (AppConstant.IS_OFFLINEMODE) {
            List<BabyInfosBean> babyListInfo = GreenDaoUtils.getInstance(mContext).getBabyListInfo();
            MineBBResBean mineBBResBean = new MineBBResBean();
            mineBBResBean.setCode("0");
            mineBBResBean.setBabyInfos(babyListInfo);
            mView.returnBBInfo(mineBBResBean);
            return;
        }
        mRxManage.add(mModel.putMineBBInfo(mineBBReqBean)
                .subscribe(new RxSubscriber<MineBBResBean>(mContext, false) {
                    @Override
                    protected void _onNext(MineBBResBean mineBBResBean) {

                        mView.returnBBInfo(mineBBResBean);

                    }

                    @Override
                    protected void _onError(String message) {
                        mView.returnErrInfo(message);
                    }
                }));


    }

    @Override
    public void getTempOneDayInfoRequest(TempHistoryDataReqBean dataReqBean, boolean isShow) {
        if (AppConstant.IS_OFFLINEMODE) {
            GreenDaoUtils.getInstance(mContext).getTempListInfoByDay(dataReqBean.queryTime, dataReqBean.type, dataReqBean.localId).flatMap(new Func1<List<TemperaturesBean>, Observable<TempOneDayDataResBean>>() {
                @Override
                public Observable<TempOneDayDataResBean> call(List<TemperaturesBean> temperaturesBeen) {
                    final TempOneDayDataResBean tempOneDayDataResBean = new TempOneDayDataResBean();
                    tempOneDayDataResBean.setMaxTempTime("");
                    tempOneDayDataResBean.setMaxTemperature("");
                    tempOneDayDataResBean.setCode("0");
                    tempOneDayDataResBean.setTemperatures(temperaturesBeen);
                    for (TemperaturesBean bean : temperaturesBeen) {
                        if (bean.getTempTime().compareTo(tempOneDayDataResBean.getMaxTempTime()) > 0)
                            tempOneDayDataResBean.setMaxTempTime(bean.getTempTime());
                        if (bean.getTemperature().compareTo(tempOneDayDataResBean.getMaxTemperature()) > 0)
                            tempOneDayDataResBean.setMaxTemperature(bean.getTemperature());
                    }
                    return Observable.create(new Observable.OnSubscribe<TempOneDayDataResBean>() {
                        @Override
                        public void call(Subscriber<? super TempOneDayDataResBean> subscriber) {
                            subscriber.onNext(tempOneDayDataResBean);
                        }
                    });
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TempOneDayDataResBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    mView.returnErrInfo(e.getMessage());

                }

                @Override
                public void onNext(TempOneDayDataResBean tempOneDayDataResBean) {
                    mView.returnBBOneDayInfo(tempOneDayDataResBean);
                }
            });
            return;
        }
        RxSubscriber<TempOneDayDataResBean> subscriber = new RxSubscriber<TempOneDayDataResBean>(mContext, isShow) {
            @Override
            protected void _onNext(TempOneDayDataResBean tempOneDayDataResBean) {
                mView.returnBBOneDayInfo(tempOneDayDataResBean);

            }

            @Override
            protected void _onError(String message) {
                mView.returnErrInfo(message);
            }
        };
        mRxManage.add(mModel.getTempOneDayInfo(dataReqBean)
                .subscribe(subscriber));
    }
}
