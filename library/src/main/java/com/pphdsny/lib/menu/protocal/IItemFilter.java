package com.pphdsny.lib.menu.protocal;

/**
 * Created by wangpeng on 2018/6/27.
 */
public interface IItemFilter<T> {

    void initData(T t);

    void setSelect(boolean isSelect);
}
