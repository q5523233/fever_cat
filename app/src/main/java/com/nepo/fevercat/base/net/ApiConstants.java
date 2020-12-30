package com.nepo.fevercat.base.net;


import com.nepo.fevercat.common.utils.BuildConfigUtils;

/**
 * 项目名: TakeOutFood
 * 包名 :  com.nepo.takeoutfood.base.net
 * 文件名:  ApiConstants
 * 作者 :   <sen>
 * 时间 :  下午2:50 2016/11/15.
 * 描述 :  服务器地址配置
 */

public class ApiConstants {

    /**
     * 服务器 URL
     * 测试
     */
    private static final String TEST_BASE_HOST = "http://192.168.0.56:8080/";


    /**
     * 正式
     */
    private static final String BASE_HOST = "http://119.23.52.205/";


    /**
     * 区分正式服务器/测试服务器 地址
     *
     * @return 服务器URL
     */
    public static String GET_HOST() {

        if (!BuildConfigUtils.DEBUG) {
            // 测试地址
            return TEST_BASE_HOST;
        } else {
            // 正式地址
            return BASE_HOST;
        }


    }


    /**
     * 获取验证码
     */
    public final static String VALIDATE_CODE = "VALIDATE_CODE";
    /**
     * 注册
     */
    public final static String REGISTER = "REGISTER";

    /**
     * 登录
     */
    public final static String LOGIN = "LOGIN";
    /**
     * 忘记密码
     */
    public final static String FORGOT_PASSWORD = "FORGOT_PASSWORD";

    /**
     * 添加亲友关注
     */
    public static final String BIND_FRIENDS_ADD = "BIND_FRIENDS_ADD";

    /**
     * 亲友关注列表
     */
    public static final String BIND_FRIENDS_LIST = "BIND_FRIENDS_LIST";

    /**
     * 修改亲友关注信息
     */
    public static final String BIND_FRIENDS_EDIT = "BIND_FRIENDS_EDIT";

    /**
     * 删除亲友列表
     */
    public static final String BIND_FRIENDS_DEL = "BIND_FRIENDS_DEL";

    /**
     * 修改用户信息
     */
    public static final String MODIFY_USER_INFO = "MODIFY_USER_INFO";
    //1-修改用户昵称 2-修改密码 3-解绑手机 4-绑定手机】
    public static final String MODIFY_USER_INFO_1 = "1";
    public static final String MODIFY_USER_INFO_2 = "2";
    public static final String MODIFY_USER_INFO_3 = "3";
    public static final String MODIFY_USER_INFO_4 = "4";

    /**
     * 编辑宝宝信息
     */
    public static final String MODIFY_BABY = "MODIFY_BABY";
    public static final String MODIFY_BABY_ADD_1 = "1";
    public static final String MODIFY_BABY_UPDATE_2 = "2";
    public static final String MODIFY_BABY_DEL_3 = "3";
    public static final String MODIFY_BABY_LIST_4 = "4";
    public static final String MODIFY_BABY_LIST_5 = "5";

    /**
     * 体温 历史记录列表
     */
    public static final String HISTORY_TEMP = "HISTORY_TEMP";


    /**
     * 体温 某一天记录
     */
    public static final String HISTORY_TEMP_ONE = "HISTORY_TEMP_ONE";
    public static final String HISTORY_TEMP_ONE_TYPE_ZERO = "0";
    public static final String HISTORY_TEMP_ONE_TYPE_FIRST = "1";
    public static final String HISTORY_TEMP_ONE_TYPE_SECOND = "2";
    public static final String HISTORY_TEMP_ONE_TYPE_THIRD = "3";
    public static final String HISTORY_TEMP_ONE_TYPE_FOUR = "4";


    /**
     * 上传宝宝温度信息
     */
    public static final String MODIFY_BABY_TEMP = "ADD_BABY_TEMP";


}
