package com.pphdsny.lib.menu.impl.single;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pphdsny.lib.menu.R;
import com.pphdsny.lib.menu.impl.model.FilterIdNameModel;
import com.pphdsny.lib.menu.listener.OnFilterItemListener;
import com.pphdsny.lib.menu.protocal.IItemFilter;

/**
 * Created by wangpeng on 2017/10/24.
 * 单选ItemLayout
 */

public class SingleFilterItemLayout extends FrameLayout implements IItemFilter<FilterIdNameModel> {

    private TextView tvItem;

    private FilterIdNameModel mItemData;
    private OnFilterItemListener<FilterIdNameModel> mSelectListener;
    private Context mContext;

    public SingleFilterItemLayout(Context context) {
        super(context);
        init(context);
    }

    public SingleFilterItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SingleFilterItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_single_filter_item, this);
        initView();
    }

    private void initView() {
        tvItem = findViewById(R.id.tv_item);
        tvItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSelect();
            }
        });
    }

    @Override
    public void initData(FilterIdNameModel itemData) {
        this.mItemData = itemData;
        if (itemData == null) return;
        tvItem.setText(itemData.getName());
        setSelect(itemData.isSelect());
    }

    private void clickSelect() {
        if (mItemData.isSelect()) {
            //如果自身已经是选中状态，则直接返回
            if (mSelectListener != null) {
                mSelectListener.onSelectChange(mItemData);
            }
            return;
        }
        mItemData.setSelect(!mItemData.isSelect());
        refreshData();
        if (mSelectListener != null) {
            mSelectListener.onSelectChange(mItemData);
        }
    }

    private void refreshData() {
        if (mItemData != null) {
            setSelect(mItemData.isSelect());
        }
    }

    public void setOnSelectListener(OnFilterItemListener<FilterIdNameModel> selectListener) {
        this.mSelectListener = selectListener;
    }

    @Override
    public void setSelect(boolean isSelect) {
        if (mItemData != null) {
            mItemData.setSelect(isSelect);
            tvItem.setText(mItemData.getName());
        }
        tvItem.setSelected(isSelect);
    }
}
