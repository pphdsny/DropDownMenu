package com.pphdsny.lib.menu.protocal;

import com.pphdsny.lib.menu.listener.OnFilterListener;

/**
 * Created by wangpeng on 2018/7/2.
 * 选中的数据
 */
public interface IFilterSelect<T> {

    void setFilterSureCallback(OnFilterListener<T> filterSureCallback);

    T getSelectFilterData();
}
