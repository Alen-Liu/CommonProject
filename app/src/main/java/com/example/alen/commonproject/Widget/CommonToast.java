package com.example.alen.commonproject.Widget;

import android.widget.Toast;

import com.example.alen.commonproject.App;


/**
 * Created by Alen on 2016/11/26.
 * 通用的toast， 防止弹出toast延时
 */
public class CommonToast {
    private static Toast mToast;

    public static Toast getInstance(String alertString, int time) {
        if (mToast == null) {
            mToast = Toast.makeText(App.mContext, alertString, time);
        } else {
            mToast.setText(alertString);
            mToast.setDuration(time);
        }
        return mToast;
    }

    public static Toast getInstance(String alertString) {
        if (mToast == null) {
            mToast = Toast.makeText(App.mContext, alertString, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(alertString);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        return mToast;
    }
}
