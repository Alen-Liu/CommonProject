package com.example.alen.commonproject;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.alen.commonproject.Manager.EventCenterManager;
import com.example.alen.commonproject.Net.ApiContant;
import com.example.alen.commonproject.Utils.Constants;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by Alen on 2017/5/8
 * 自己用的！！！！    做为项目快速启动方案
 *
 *
 * 这是一套公用的项目启动基本框架
 * 使用 LiteHttp + Glide（之前用的是ImageLoader） + pulltorefresh + greendao 外加一些基本的常用控件的模板。
 * 使用这套框架  可以快速的进行项目启动。
 * 1. 接口请求 实例
 * 2. 图片异步加载
 * 3. 常用下拉刷新ListView ，ScrollView (刷新效果可以自定义)
 * 4. 常用的圆角ImageView， 圆形ImageView，正方形ImageView， 右滑删除控件， Toast， 类似ios的dialog弹框，
 * 日期选择，图片选择， 横向单行GridView，价格输入框， 自定义ClearEditText
 * 都在Widget中，可以按照自己的需要进行删减。
 * 5. android 原生的webview 有很多适配和 内存泄漏的问题。我这里直接用了 腾讯的TBS，这样保证能在qq中打开的页面
 * 我们项目都可以打开（如果在qq中打不开的页面，说明h5 写的有问题 ！！！***嘻嘻嘻***！！！）
 * 6. 日常项目中由于加入了很多第三方库，很容易超过方法数限制， 所以这里默认设置支持 multiDex
 * 7. 自定义了一个 消息中心管理类，方便在不同的页面之间进行 消息通信。
 * 8. Utils 这个类是我在项目中用到的一些公用方法，可以删掉其中不用的。
 *
 * 项目中还有很多其他的注意点，例如打包的时候自增版本号， 打包的时候配置多个渠道打包， 正式和测试环境的各个属性值的配置
 * 项目打点，版本更新，用户信息的单例， 缓存文件目录和缓存清除， webview 和native的JS交互等等，这些在项目启动的时候也需要考虑。
 *
 *
 * 很多内容都是在github上的开源库中拿来修改直接用， 在这里特别感谢github上的各位大神
 */

public class App extends MultiDexApplication { // 这里支持MultiDex
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        //初始化app配置
        ApiContant.setServerHost();// 配置测试环境和正式环境的 接口请求地址
        Constants.setImageHost();// 配置图片加载服务的 测试和正式环境加载头地址
        EventCenterManager.initEventCenterManager(); // 初始化 自定义的 消息中心管理类


        //初始化X5 webview的相关
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                //Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }
}
