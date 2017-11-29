package com.example.alen.commonproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Alen on 2017/4/19.
 */

public class MyBaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 防止 glide   java.lang.IllegalArgumentException: You cannot start a load for a destroyed activity
//        if (Util.isOnMainThread() && !this.isFinishing()) {
//            Glide.with(this).pauseRequests();
//        }
    }
}
