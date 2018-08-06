package com.pphdsny.lib.menu.protocal;

import android.view.View;

/**
 * 筛选项tab
 *
 * @param <T> 入参数据类型
 * @param <K> 选中参数数据类型
 */
public interface ITabFilter<T, K> extends IFilterSelect<K> {

    /**
     * 获取筛选View
     *
     * @return
     */
    View getFilterView();

    /**
     * 获取筛选tabView
     *
     * @return
     */
    View getTabView();

    /**
     * 初始化数据
     *
     * @param t
     */
    void initData(T t);

    /**
     * 设置选中项数据
     *
     * @param t
     */
    void setSelectFilterDate(T t);

    /**
     * 清空选中状态
     */
    void cleanFilter();

    /**
     * 显示筛选项
     */
    void show();

    /**
     * 隐藏筛选项
     */
    void dismiss();

    /**
     * 筛选项是否可见
     *
     * @return
     */
    boolean isShowing();
}
