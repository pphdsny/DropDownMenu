package com.pphdsny.lib.menu.impl.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeng on 2017/10/24.
 */

public class FilterGroupModel extends PPModel{
    private String name;
    private List<FilterIdNameModel> items;

    public FilterGroupModel(String name, List<FilterIdNameModel> items) {
        this.name = name;
        this.items = items;
    }

    public FilterGroupModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterIdNameModel> getItems() {
        return items;
    }

    public void setItems(List<FilterIdNameModel> items) {
        this.items = items;
    }

    public boolean dataCanUse() {
        return items != null && items.size() > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FilterGroupModel) {
            return TextUtils.equals(name, ((FilterGroupModel) obj).getName());
        }
        return false;
    }

    public FilterGroupModel copy() {
        FilterGroupModel retFilterGrouoModel = new FilterGroupModel();
        retFilterGrouoModel.setName(name);
        ArrayList<FilterIdNameModel> filterIdNameModels = new ArrayList<>();
        retFilterGrouoModel.setItems(filterIdNameModels);
        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                filterIdNameModels.add(items.get(i).copy());
            }
        }
        return retFilterGrouoModel;
    }

    public static List<FilterGroupModel> copyList(List<FilterGroupModel> list) {
        List<FilterGroupModel> retList = new ArrayList<>();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                FilterGroupModel model = list.get(i);
                if (model != null)
                    retList.add(model.copy());
            }
        }
        return retList;
    }

    @Override
    public String toString() {
        return "FilterGroupModel{" +
                "name='" + name + '\'' +
                ", items=" + items +
                '}';
    }
}
