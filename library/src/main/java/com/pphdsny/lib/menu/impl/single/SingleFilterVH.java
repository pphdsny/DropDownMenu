package com.pphdsny.lib.menu.impl.single;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.pphdsny.lib.menu.R;
import com.pphdsny.lib.menu.databinding.LayoutFilterSingleBinding;
import com.pphdsny.lib.menu.databinding.LayoutFilterTabBinding;
import com.pphdsny.lib.menu.impl.model.FilterGroupModel;
import com.pphdsny.lib.menu.impl.model.FilterIdNameModel;
import com.pphdsny.lib.menu.listener.OnFilterItemListener;
import com.pphdsny.lib.menu.listener.OnFilterListener;
import com.pphdsny.lib.menu.protocal.IItemFilter;
import com.pphdsny.lib.menu.protocal.ITabFilter;
import com.pphdsny.lib.menu.util.ArrayUtils;

import java.util.ArrayList;

/**
 * Created by wangpeng on 2018/6/27.
 * 单选筛选条件
 */
public class SingleFilterVH implements ITabFilter<FilterGroupModel, FilterGroupModel> {

    private final Context mContext;
    private LayoutFilterSingleBinding contentBinding;
    private LayoutFilterTabBinding tabBinding;
    private OnFilterListener<FilterGroupModel> filterSureCallback;
    //数据
    private FilterGroupModel initFilterGroupData;
    private FilterGroupModel selectFilterGroupData;

    private boolean isShowing;

    public SingleFilterVH(Context context) {
        this.mContext = context;
        contentBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_filter_single, null, false);
        tabBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_filter_tab, null, false);
    }

    @Override
    public View getFilterView() {
        return contentBinding.getRoot();
    }

    @Override
    public View getTabView() {
        return tabBinding.getRoot();
    }

    @Override
    public void initData(FilterGroupModel filterGroupModel) {
        if (contentBinding == null) return;
        this.initFilterGroupData = filterGroupModel;
        if (filterGroupModel != null) {
            contentBinding.llContentWrap.removeAllViews();
            for (int i = 0; i < filterGroupModel.getItems().size(); i++) {
                addItem(filterGroupModel.getItems().get(i));
            }
        }
        setSelectFilterDate(filterGroupModel);
    }

    private void addItem(FilterIdNameModel filterIdNameModel) {
        if (contentBinding == null) return;
        SingleFilterItemLayout filterItemLayout = new SingleFilterItemLayout(mContext);
        filterItemLayout.initData(filterIdNameModel);
        filterItemLayout.setTag(getViewTagById(filterIdNameModel.getId()));
        filterItemLayout.setOnSelectListener(new OnFilterItemListener<FilterIdNameModel>() {
            @Override
            public void onSelectChange(FilterIdNameModel itemData) {
                clickItemSelect(itemData);
            }
        });
        contentBinding.llContentWrap.addView(filterItemLayout);
    }

    private void clickItemSelect(FilterIdNameModel itemData) {
        if (initFilterGroupData != null && !ArrayUtils.isEmptyList(selectFilterGroupData.getItems())) {
            FilterIdNameModel filterIdNameModel = selectFilterGroupData.getItems().get(0);
            if (itemData.getId() != filterIdNameModel.getId()) {
                setItemViewSelect(filterIdNameModel.getId(), false);
            }
        }
        selectFilterGroupData.getItems().clear();
        if (itemData.isNoLimitItem()) {
            //不限不选中，直接返回
            filterCallback();
            return;
        }
        selectFilterGroupData.getItems().add(itemData);
        cancelNoLimitItem();
        filterCallback();
    }

    private void cancelNoLimitItem() {
        setItemViewSelect(FilterIdNameModel.NO_LIMIT_ITEM_ID, false);
    }

    private void filterCallback() {
        if (filterSureCallback != null) {
            filterSureCallback.onFilter(getSelectFilterData());
        }
        updateTabText();
    }

    private void setItemViewSelect(int id, boolean isSelect) {
        View viewWithTag = contentBinding.llContentWrap.findViewWithTag(getViewTagById(id));
        if (viewWithTag != null && viewWithTag instanceof IItemFilter) {
            ((IItemFilter) viewWithTag).setSelect(isSelect);
        }
    }

    private String getViewTagById(int id) {
        return "filter_single_item_" + id;
    }

    @Override
    public void setSelectFilterDate(FilterGroupModel filterGroupModel) {
        if (filterGroupModel != null && filterGroupModel.getItems() != null) {
            selectFilterGroupData = new FilterGroupModel();
            selectFilterGroupData.setName(filterGroupModel.getName());
            selectFilterGroupData.setItems(new ArrayList<FilterIdNameModel>());
            for (int i = 0; i < filterGroupModel.getItems().size(); i++) {
                FilterIdNameModel filterIdNameModel = filterGroupModel.getItems().get(i);
                if (filterIdNameModel.isSelect()) {
                    selectFilterGroupData.getItems().add(filterIdNameModel);
                    //view选中
                    setItemViewSelect(filterIdNameModel.getId(), filterIdNameModel.isSelect());
                }
            }
        }
        updateTabText();
    }

    private void updateTabText() {
        if (selectFilterGroupData != null && !ArrayUtils.isEmptyList(selectFilterGroupData.getItems())) {
            if (selectFilterGroupData.getItems().size() > 1) {
                tabBinding.tvFilter.setText("多选");
            } else {
                tabBinding.tvFilter.setText(selectFilterGroupData.getItems().get(0).getName());
            }
            tabBinding.tvFilter.setTextSelect(true, hasSelectData());
        } else if (initFilterGroupData != null) {
            tabBinding.tvFilter.setText(initFilterGroupData.getName());
            tabBinding.tvFilter.setTextSelect(false, hasSelectData());
        } else {
            tabBinding.tvFilter.setTextSelect(false, hasSelectData());
        }
    }

    @Override
    public FilterGroupModel getSelectFilterData() {
        return selectFilterGroupData;
    }

    @Override
    public void cleanFilter() {
        if (selectFilterGroupData == null || ArrayUtils.isEmptyList(selectFilterGroupData.getItems())) {
            return;
        }
        for (int i = 0; i < selectFilterGroupData.getItems().size(); i++) {
            FilterIdNameModel filterIdNameModel = selectFilterGroupData.getItems().get(i);
            filterIdNameModel.setSelect(false);
            setItemViewSelect(filterIdNameModel.getId(), false);
        }
        selectFilterGroupData.getItems().clear();
        updateTabText();
    }

    @Override
    public void show() {
        isShowing = true;
        tabBinding.tvFilter.setTextSelect(true, hasSelectData());
        tabBinding.tvFilter.setArrowSelect(true);
        contentBinding.getRoot().setVisibility(View.VISIBLE);
        tabBinding.ivFilterArrow.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss() {
        isShowing = false;
        tabBinding.tvFilter.setTextSelect(false, hasSelectData());
        tabBinding.tvFilter.setArrowSelect(false);
        contentBinding.getRoot().setVisibility(View.GONE);
        tabBinding.ivFilterArrow.setVisibility(View.GONE);
    }

    private boolean hasSelectData() {
        if (selectFilterGroupData == null || ArrayUtils.isEmptyList(selectFilterGroupData.getItems())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setFilterSureCallback(OnFilterListener<FilterGroupModel> filterSureCallback) {
        this.filterSureCallback = filterSureCallback;
    }
}
