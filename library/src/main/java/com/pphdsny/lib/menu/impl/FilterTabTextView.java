package com.pphdsny.lib.menu.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import com.pphdsny.lib.menu.R;
import com.pphdsny.lib.menu.util.DisplayUtils;

/**
 * Created by wangpeng on 2018/6/29.
 */
public class FilterTabTextView extends android.support.v7.widget.AppCompatTextView {
    private Context mContext;

    public FilterTabTextView(Context context) {
        this(context, null);
    }

    public FilterTabTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterTabTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setTextSize(14);
        setTextColor(ContextCompat.getColorStateList(context, R.color.selector_filter_item));
        setLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
        setGravity(Gravity.CENTER_VERTICAL);
        setCompoundDrawablePadding(DisplayUtils.dip2px(mContext, 4));
    }

    public void setTextSelect(boolean isSelect, boolean isHasData) {
        if (isHasData) {
            setSelected(true);
            return;
        }
        setSelected(isSelect);
    }

    public void setArrowSelect(boolean isSelect) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), isSelect ? R.mipmap.icon_arrow_red_up : R.mipmap.icon_arrow_gray_down);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
        bitmapDrawable.setBounds(0, 0, DisplayUtils.dip2px(mContext, 10), DisplayUtils.dip2px(mContext, 6));
        setCompoundDrawables(null, null, bitmapDrawable, null);
    }

}
