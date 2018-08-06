package com.pphdsny.lib.menu.protocal;

import android.view.View;

/**
 * Created by wangpeng on 2018/6/27.
 * 搜索筛选tab
 */
public interface ITabFilter<T, K> extends IFilterSelect<K> {

    View getFilterView();

    View getTabView();

    void initData(T t);

    void setSelectFilterDate(T t);

    void cleanFilter();

    void show();

    void dismiss();

    boolean isShowing();
}
