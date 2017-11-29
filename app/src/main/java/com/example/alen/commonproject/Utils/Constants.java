package com.example.alen.commonproject.Utils;

/**
 * Created by Alen on 2017/11/28.
 */

public class Constants {
    public static String IMAGE_LOAD_HEADER = "http://xxxx1.com/";


    // 项目中公用常量
    public static final String INTENT_OPEN_URL = "open_url";

    // 设置一下测试和正式环境的图片上传地址
    // 需要在app启动的时候马上进行设置
    public static void setImageHost() {
        if (Utils.isDebug()) {
            IMAGE_LOAD_HEADER = "http://xxxx1.com/";
        } else {
            IMAGE_LOAD_HEADER = "http://xxxx2.com/";
        }
    }
}
