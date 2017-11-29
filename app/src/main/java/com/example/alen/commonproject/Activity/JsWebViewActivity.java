package com.example.alen.commonproject.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.JavascriptInterface;

import com.example.alen.commonproject.MyBaseActivity;
import com.example.alen.commonproject.R;
import com.example.alen.commonproject.Utils.Constants;
import com.example.alen.commonproject.Utils.Utils;
import com.example.alen.commonproject.Widget.CommonToast;
import com.example.alen.commonproject.Widget.CustomDialog;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * Created by Alen on 2017/7/3.
 * <p>
 * 通用的注册 与 H5 调用的 webview Activity
 */

public class JsWebViewActivity extends MyBaseActivity {
    private WebView contentWebView;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_js_webview);
        mUrl = getIntent().getStringExtra(Constants.INTENT_OPEN_URL);
        if (Utils.isStringEmpty(mUrl)) finish();

        getWindow().setFormat(PixelFormat.TRANSLUCENT);// X5 网页中的视频，上屏幕的时候，可能出现闪烁的情况
        this.initView();
    }

    private void initView() {
        contentWebView = (WebView) findViewById(R.id.js_webview);
//        contentWebView.setScrollbarFadingEnabled(false);
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        contentWebView.getSettings().setSupportMultipleWindows(false);// 这个不要修改， 否则 <a href 会打不开
        contentWebView.setWebContentsDebuggingEnabled(true);
        contentWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        contentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://") || url.startsWith("https://")) {

                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                        return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                    }
                }
                //处理http和https开头的url
                view.loadUrl(url);
                return true;
            }
        });
        contentWebView.setWebChromeClient(new WebChromeClient(){ // 这里一定要自己重写，否则会不支持 js页面的alert/confirm方法
            @Override
            public boolean onJsAlert(WebView webView, String s, String s1, final JsResult jsResult) {
                CustomDialog.Builder builder = new CustomDialog.Builder(JsWebViewActivity.this);
                builder.setMessage("" +s1);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jsResult.confirm();
                        dialog.dismiss();
                    }
                });
                CustomDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, final JsResult jsResult) {
                CustomDialog.Builder builder = new CustomDialog.Builder(JsWebViewActivity.this);
                builder.setMessage("" +s1);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jsResult.confirm();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jsResult.cancel();
                        dialog.dismiss();
                    }
                });
                CustomDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, final JsPromptResult jsPromptResult) {
                CustomDialog.Builder builder = new CustomDialog.Builder(JsWebViewActivity.this);
                builder.setMessage("" +s1);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jsPromptResult.confirm();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jsPromptResult.cancel();
                        dialog.dismiss();
                    }
                });
                CustomDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                return true;
            }
        });
        contentWebView.loadUrl(mUrl);
        contentWebView.addJavascriptInterface(JsWebViewActivity.this, "Android");

        contentWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        contentWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        contentWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
        contentWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        contentWebView.getSettings().setDatabaseEnabled(true);
        contentWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onPause() {
        contentWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        contentWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        contentWebView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {//
        super.onBackPressed();
//        if (contentWebView.canGoBack()) contentWebView.goBack();
//        else
    }

    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android
    // 调用这个接口可以直接关闭当前的Activity
    @JavascriptInterface
    public void onBackImagePress() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommonToast.getInstance("成功调用了 原生的接口").show();
            }
        });
    }


    // 测试方法  这里马上调用了 网页上的JS方法
    @JavascriptInterface
    public void startFunction() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contentWebView.loadUrl("javascript:" + "function_name"
                        + "('param1','param2 ','param3','param4')");
            }
        });
    }

}
