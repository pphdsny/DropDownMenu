package com.pphdsny.app.menu.util;

import com.pphdsny.app.menu.data.FilterComplexConditionModel;
import com.pphdsny.lib.menu.impl.model.FilterGroupModel;
import com.pphdsny.lib.menu.impl.model.FilterIdNameModel;
import com.pphdsny.lib.menu.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangpeng on 2017/10/24.
 */

public class FilterModelConvertUtil {

    public static FilterIdNameModel convertToFilterIdNameModel(FilterComplexConditionModel.ConditionModel.OptionsModel optionsModel) {
        return new FilterIdNameModel(optionsModel.getCode(), optionsModel.getLabel());
    }

    public static List<FilterIdNameModel> convertToFilterIdNameListModel(List<FilterComplexConditionModel.ConditionModel.OptionsModel> optionsModels) {
        List<FilterIdNameModel> retList = new ArrayList<>();
        if (ArrayUtils.isEmptyList(optionsModels)) {
            return retList;
        }
        for (int i = 0; i < optionsModels.size(); i++) {
            retList.add(convertToFilterIdNameModel(optionsModels.get(i)));
        }
        return retList;
    }

    public static FilterGroupModel convertToFilterGroupModel(FilterComplexConditionModel.ConditionModel conditionModel) {
        FilterGroupModel filterGroupModel = new FilterGroupModel();
        if (conditionModel != null) {
            filterGroupModel.setName(conditionModel.getChunkName());
            filterGroupModel.setItems(convertToFilterIdNameListModel(conditionModel.getOptions()));
        }else{
            filterGroupModel.setItems(new ArrayList<FilterIdNameModel>());
        }
        return filterGroupModel;
    }

}
