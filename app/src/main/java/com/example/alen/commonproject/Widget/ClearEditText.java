package com.example.alen.commonproject.Widget;

/**
 * Created by Alen on 2017/2/27.
 * 自带清楚按钮的EditText
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.example.alen.commonproject.R;
import com.example.alen.commonproject.Utils.Utils;

public class ClearEditText extends EditText implements OnFocusChangeListener,
        TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    private Drawable mTransDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    private int mPaddingLeft;

    //禁止输入Emoji
    private int cursorPos;
    private String inputAfterText;
    private boolean resetText;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Common_ClearEditText);
        int deleDrawable = a.getResourceId(R.styleable.Common_ClearEditText_delete_icon, R.mipmap.nav_icon_search_delete);
        // 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        mTransDrawable = getCompoundDrawables()[0];
        if (mClearDrawable == null) {
            // throw new
            // NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(deleDrawable);
        }
        if (mTransDrawable == null) {
            mTransDrawable = getResources().getDrawable(android.R.color.transparent);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());

        mTransDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());
        // 默认设置隐藏图标
        setClearIconVisible(false);
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
        mPaddingLeft = getPaddingLeft();
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件 当我们按下的位置 在 EditText的宽度 -
     * 图标到控件右边的间距 - 图标的宽度 和 EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        Drawable left = null;
        if ((getGravity() == Gravity.CENTER_HORIZONTAL || getGravity() == Gravity.CENTER) && right != null) {
            left = mTransDrawable;
        }
        setCompoundDrawables(left,
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
        if (!resetText && getSelectionEnd() > cursorPos) {
            CharSequence input = s.subSequence(cursorPos, getSelectionEnd());
            if (Utils.containsEmoji(input.toString())) {
                resetText = true;
                CommonToast.getInstance("不支持输入Emoji表情字符").show();
                setText(inputAfterText);
                CharSequence text = getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
            }
        } else {
            resetText = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!resetText) {
            cursorPos = getSelectionEnd();
            inputAfterText = s.toString();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}