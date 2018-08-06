package com.pphdsny.lib.menu;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.pphdsny.lib.menu.databinding.LayoutFilterBinding;
import com.pphdsny.lib.menu.protocal.ITabFilter;

import java.util.List;

/**
 * Created by wangpeng on 2018/8/6.
 */
public class PPFilterLayout extends FrameLayout {
    private LayoutFilterBinding binding;
    private Context mContext;

    private List<ITabFilter> tabFilters;
    private ITabFilter currentShowFilter;

    public PPFilterLayout(@NonNull Context context) {
        this(context, null);
    }

    public PPFilterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PPFilterLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_filter, this, true);
        binding.flFilterWrap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFilterLayout();
            }
        });
    }

    public void initTab(List<ITabFilter> tabs) {
        this.tabFilters = tabs;
        if (tabs == null) {
            return;
        }
        for (int i = 0; i < tabs.size(); i++) {
            ITabFilter iTabFilter = tabs.get(i);
            addTab(iTabFilter);
            addFilterView(iTabFilter);
            if (i < tabs.size() - 1) {
                addTabDivideLine();
            }
        }
    }

    public List<ITabFilter> getTabFilters() {
        return tabFilters;
    }

    private void addFilterView(ITabFilter tab) {
        View filterView = tab.getFilterView();
        filterView.setVisibility(GONE);
        binding.flFilterBodyWrap.addView(filterView);
    }

    private void addTabDivideLine() {
        View divideLineView = new View(mContext);
        divideLineView.setBackgroundResource(R.color.tab_bg_color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelOffset(R.dimen.tab_divide_width), mContext.getResources().getDimensionPixelOffset(R.dimen.tab_divide_height));
        params.gravity = Gravity.CENTER_VERTICAL;
        binding.llFilter.addView(divideLineView, params);
    }

    private void addTab(final ITabFilter tab) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        View tabView = tab.getTabView();
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterLayout(tab);
            }
        });
        binding.llFilter.addView(tabView, layoutParams);
    }

    private void showFilterLayout(ITabFilter tab) {
        if (tab == null) return;
        if (currentShowFilter == tab && tab.isShowing()) {
            //重复点击当前tab，显示前应该先隐藏
            dismissFilterLayout();
            return;
        }
        if (currentShowFilter != null) {
            currentShowFilter.dismiss();
        }
        currentShowFilter = tab;
        currentShowFilter.show();
        binding.flFilterWrap.setVisibility(VISIBLE);
    }

    public void dismissFilterLayout() {
        if (currentShowFilter != null) {
            currentShowFilter.dismiss();
        }
        binding.flFilterWrap.setVisibility(GONE);
    }

    public void cleanFilter() {
        if (tabFilters == null || tabFilters.size() == 0) {
            return;
        }
        for (int i = 0; i < tabFilters.size(); i++) {
            tabFilters.get(i).cleanFilter();
        }
    }

}