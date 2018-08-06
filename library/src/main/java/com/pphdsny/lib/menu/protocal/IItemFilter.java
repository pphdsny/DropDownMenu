package com.pphdsny.lib.menu.protocal;

/**
 * Created by wangpeng on 2018/6/27.
 */
public interface IItemFilter<T> {

    /**
     * 初始化数据
     *
     * @param t
     */
    void initData(T t);

    /**
     * 设置子项选中否
     *
     * @param isSelect
     */
    void setSelect(boolean isSelect);
}
