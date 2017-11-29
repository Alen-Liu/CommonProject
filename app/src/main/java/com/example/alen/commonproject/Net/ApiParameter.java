package com.example.alen.commonproject.Net;


import com.example.alen.commonproject.Utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alen on 2017/2/11.
 * <p>
 * 请求接口的父类
 */
public class ApiParameter {

    // 子类 如果要添加参数需要重写该方法， 否则默认为无 额外参数
    //
    public ApiParamMap buildExterParameter() {
        return new ApiParamMap();
    }

    // 子类， 如果需要添加sessionKey 需要覆盖此函数并且返回true
    public boolean needSessionKey() {
        return false;
    }


    public String getRequestString() {
        ApiParamMap apiParamMap = buildExterParameter();
        apiParamMap.put("os", new ApiParamMap.ParamData("android"));
        apiParamMap.put("appVersion", new ApiParamMap.ParamData(Utils.getVersion()));
        apiParamMap.put("versionCode", new ApiParamMap.ParamData(Utils.getVersionCode() + ""));
        apiParamMap.put("channel", new ApiParamMap.ParamData(Utils.getChannelName()));
        List<String> lists = new ArrayList<String>(apiParamMap.keySet());
        String result = "";
        for (String key : lists) {
            result += key + "=" + apiParamMap.get(key).value + "&";
        }
        result += "osVersion=" + android.os.Build.VERSION.SDK_INT + "";
        return "?" + result;
    }
}
