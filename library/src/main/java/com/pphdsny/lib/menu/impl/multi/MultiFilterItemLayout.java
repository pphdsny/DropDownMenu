package com.pphdsny.lib.menu.impl.multi;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.pphdsny.lib.menu.R;
import com.pphdsny.lib.menu.impl.model.FilterIdNameModel;
import com.pphdsny.lib.menu.listener.OnFilterItemListener;
import com.pphdsny.lib.menu.protocal.IItemFilter;

/**
 * Created by wangpeng on 2017/3/15.
 * 多选筛选item layout
 */

public class MultiFilterItemLayout extends FrameLayout implements IItemFilter<FilterIdNameModel> {

    private TextView tvItemName;
    private ImageView ivItemSelect;

    private OnFilterItemListener<FilterIdNameModel> mSelectListener;
    private FilterIdNameModel mItemData;

    public MultiFilterItemLayout(Context context) {
        super(context);
        init(context);
    }

    public MultiFilterItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MultiFilterItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_multi_filter_item, this);
        initView();
    }

    private void initView() {
        tvItemName = findViewById(R.id.tv_item_name);
        ivItemSelect = findViewById(R.id.iv_item_select);
    }

    @Override
    public void initData(FilterIdNameModel itemData) {
        this.mItemData = itemData;
        refreshData();
        //click
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSelect();
            }

        });
    }

    private void refreshData() {
        if (mItemData != null) {
            setSelect(mItemData.isSelect());
        }
    }

    @Override
    public void setSelect(boolean isSelect) {
        if (mItemData != null) {
            mItemData.setSelect(isSelect);
            tvItemName.setText(mItemData.getName());
            setSelectData(mItemData.isSelect());
            ivItemSelect.setVisibility(mItemData.isNoLimitItem() ? GONE : VISIBLE);
        }
    }

    private void setSelectData(boolean isSelect) {
        tvItemName.setSelected(isSelect);
        ivItemSelect.setImageResource(isSelect ? R.mipmap.icon_muilt_filter_select : R.mipmap.icon_muilt_filter_unselect);
    }

    private void clickSelect() {
        if (mItemData == null) {
            return;
        }
        mItemData.setSelect(!mItemData.isSelect());
        setSelectData(mItemData.isSelect());
        if (mSelectListener != null) {
            mSelectListener.onSelectChange(mItemData);
        }
    }

    public FilterIdNameModel getModel() {
        return mItemData;
    }

    public void setOnSelectListener(OnFilterItemListener<FilterIdNameModel> selectListener) {
        this.mSelectListener = selectListener;
    }
}
