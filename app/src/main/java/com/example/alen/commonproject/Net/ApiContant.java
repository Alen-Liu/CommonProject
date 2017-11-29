package com.example.alen.commonproject.Net;


import com.example.alen.commonproject.Utils.Utils;

/**
 * Created by Alen on 2017/2/11.
 */
public class ApiContant {
    public static String URL_HEAD = "http://182.92.154.225:9999";// http://59.110.142.102:9999

    //网络请求地址
    public static String REQUEST_LOGIN_URL = "/api/user/login";

    // 设置一下测试和正式环境的ip地址
    // 需要在app启动的时候马上进行设置
    public static void setServerHost() {
        if (Utils.isDebug()) {
            URL_HEAD = "http://182.92.154.225:9999";//测试
        } else {
            URL_HEAD = "http://59.110.142.102:9999";//正式环境
        }
    }
}
