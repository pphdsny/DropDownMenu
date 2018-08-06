package com.pphdsny.lib.menu.impl.model;

/**
 * Id-Name filter Model
 * Created by hxw on 2017-06-07.
 */

public class FilterIdNameModel extends PPModel {
    public static final int NO_LIMIT_ITEM_ID = Integer.MIN_VALUE;       //不限的筛选id

    private int id;
    private String name;
    private boolean select;

    public FilterIdNameModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isNoLimitItem() {
        return id == NO_LIMIT_ITEM_ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FilterIdNameModel) {
            return ((FilterIdNameModel) obj).id == this.id;
        }
        return false;
    }

    public FilterIdNameModel copy() {
        FilterIdNameModel retFilterIdNameModel = new FilterIdNameModel(id, name);
        retFilterIdNameModel.setSelect(select);
        return retFilterIdNameModel;
    }

    @Override
    public String toString() {
        return "FilterIdNameModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", select=" + select +
                '}';
    }
}
