package com.example.alen.commonproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.alen.commonproject.MyBaseActivity;
import com.example.alen.commonproject.R;

/**
 * Created by Alen on 2017/11/28.
 */

public class MainActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }
}
