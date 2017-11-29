package com.example.alen.commonproject.Widget;

/**
 * Created by Alen on 2017/7/21.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewForScrollView extends ListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            super.onMeasure(widthMeasureSpec, expandSpec);
        } catch (Exception e) {

        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        // TODO Auto-generated method stub
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                this.requestDisallowInterceptTouchEvent(true);
//                break;
//            default:
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
}
