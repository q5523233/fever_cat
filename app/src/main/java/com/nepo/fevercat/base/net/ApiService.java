package com.nepo.fevercat.base.net;

import com.nepo.fevercat.ui.data.bean.TempHistoryDataReqBean;
import com.nepo.fevercat.ui.data.bean.TempHistoryDataResBean;
import com.nepo.fevercat.ui.data.bean.TempOneDayDataResBean;
import com.nepo.fevercat.ui.follow.bean.FollowAddReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowEditReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListReqBean;
import com.nepo.fevercat.ui.follow.bean.FollowListResBean;
import com.nepo.fevercat.ui.main.bean.BaseResBean;
import com.nepo.fevercat.ui.main.bean.MainForgetReqBean;
import com.nepo.fevercat.ui.main.bean.MainLoginReqBean;
import com.nepo.fevercat.ui.main.bean.MainLoginResBean;
import com.nepo.fevercat.ui.main.bean.MainRegisterReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateReqBean;
import com.nepo.fevercat.ui.main.bean.MainValidateResBean;
import com.nepo.fevercat.ui.mine.bean.MineBBReqBean;
import com.nepo.fevercat.ui.mine.bean.MineBBResBean;
import com.nepo.fevercat.ui.mine.bean.MineEditReqBean;
import com.nepo.fevercat.ui.mine.bean.MineUploadResBean;
import com.nepo.fevercat.ui.real.bean.BabyTempAddReqBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by <sen> on 2016/11/4.
 *
 * 网络请求接口配置
 *
 */

public interface ApiService {

    // 基础host
    String host="system/service/data.html";
    String uploadHost="system/file/upload.html";


    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST(uploadHost)
    Observable<MineUploadResBean> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts);

    /**
     * 获取验证码
     */
    @POST(host)
    Observable<MainValidateResBean> getCodeInfo(@Body MainValidateReqBean mainValidateReqBean);

    /**
     * 注册
     */
    @POST(host)
    Observable<BaseResBean> getRegisterInfo(@Body MainRegisterReqBean mainRegisterReqBean);

    /**
     * 登录
     */
    @POST(host)
    Observable<MainLoginResBean> getLoginInfo(@Body MainLoginReqBean mainLoginReqBean);

    /**
     * 找回密码
     */
    @POST(host)
    Observable<BaseResBean> getForgetPwdInfo(@Body MainForgetReqBean forgetReqBean);

    /**
     * 添加好友
     */
    @POST(host)
    Observable<BaseResBean> putFriendInfo(@Body FollowAddReqBean followAddReqBean);

    /**
     * 获取好友
     */
    @POST(host)
    Observable<FollowListResBean> getFollowListInfo(@Body FollowListReqBean followListReqBean);

    /**
     * 删除好友
     */
    @POST(host)
    Observable<BaseResBean> putFollowInfo(@Body FollowListReqBean followListReqBean);

    /**
     * 修改关注信息
     */
    @POST(host)
    Observable<BaseResBean> editFollowInfo(@Body FollowEditReqBean followEditReqBean);

    /**
     * 修改用户信息
     */
    @POST(host)
    Observable<BaseResBean> putMineInfo(@Body MineEditReqBean mineEditReqBean);

    /**
     * 编辑宝宝信息
     */
    @POST(host)
    Observable<MineBBResBean> putMineBBInfo(@Body MineBBReqBean mineBBReqBean);

    /**
     * 温度 历史记录列表
     */
    @POST(host)
    Observable<TempHistoryDataResBean> getHistoryListInfo(@Body TempHistoryDataReqBean tempHistoryDataReqBean);

    /**
     * 温度 获取某一天记录
     */
    @POST(host)
    Observable<TempOneDayDataResBean> getOneDayDataInfo(@Body TempHistoryDataReqBean tempHistoryDataReqBean);

    /**
     * 上传宝宝温度
     */
    @POST(host)
    Observable<BaseResBean> putUploadBBTempInfo(@Body BabyTempAddReqBean babyTempAddReqBean);

}
