package com.pphdsny.app.menu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.pphdsny.app.menu.data.FilterCacheParam;
import com.pphdsny.app.menu.data.FilterComplexConditionModel;
import com.pphdsny.app.menu.databinding.ActivityMainBinding;
import com.pphdsny.app.menu.util.FilterModelConvertUtil;
import com.pphdsny.lib.cache.CacheFactory;
import com.pphdsny.lib.cache.impl.AssetCache;
import com.pphdsny.lib.cache.impl.DefaultCacheStrategy;
import com.pphdsny.lib.cache.impl.LocalCache;
import com.pphdsny.lib.cache.impl.MemoryCache;
import com.pphdsny.lib.menu.impl.model.FilterGroupModel;
import com.pphdsny.lib.menu.impl.model.FilterIdNameModel;
import com.pphdsny.lib.menu.impl.multi.MultiFilterVH;
import com.pphdsny.lib.menu.impl.single.SingleFilterVH;
import com.pphdsny.lib.menu.listener.OnFilterListener;

import java.util.ArrayList;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViewData();
    }

    private void initViewData() {
        //缓存策略，默认从列表中按序读取
        DefaultCacheStrategy<FilterComplexConditionModel> cacheStrategy = new DefaultCacheStrategy<>(
                new MemoryCache<FilterComplexConditionModel>(),
                new LocalCache<FilterComplexConditionModel>(),
                new AssetCache<FilterComplexConditionModel>()
        );
        final CacheFactory<FilterComplexConditionModel> cacheFactory = new CacheFactory<>(cacheStrategy);
        final FilterCacheParam filterCacheParam = new FilterCacheParam(1002);
        cacheFactory.getData(filterCacheParam)
                .subscribe(new Action1<FilterComplexConditionModel>() {
                    @Override
                    public void call(FilterComplexConditionModel filterComplexConditionModel) {
                        //将数据保存起来
                        cacheFactory.saveData(filterCacheParam,filterComplexConditionModel);
                        setTabData(filterComplexConditionModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "没有有效数据", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setTabData(FilterComplexConditionModel filterComplexConditionModel) {
        //总价
        MultiFilterVH priceFilterVH = new MultiFilterVH(this);
        FilterGroupModel priceFilterData = FilterModelConvertUtil.convertToFilterGroupModel(filterComplexConditionModel.getPrice());
        priceFilterData.getItems().add(0, new FilterIdNameModel(FilterIdNameModel.NO_LIMIT_ITEM_ID, "不限"));
        priceFilterVH.setFilterSureCallback(new OnFilterListener<FilterGroupModel>() {
            @Override
            public void onFilter(FilterGroupModel filterGroupModel) {
                Toast.makeText(MainActivity.this, filterGroupModel.toString(), Toast.LENGTH_SHORT).show();
                binding.filterLayout.dismissFilterLayout();
            }
        });
        priceFilterVH.initData(priceFilterData);
        //过滤条件
        SingleFilterVH sortFilterVH = new SingleFilterVH(this);
        FilterGroupModel sortFilterData = FilterModelConvertUtil.convertToFilterGroupModel(filterComplexConditionModel.getSmartOrder());
        sortFilterVH.setFilterSureCallback(new OnFilterListener<FilterGroupModel>() {
            @Override
            public void onFilter(FilterGroupModel filterGroupModel) {
                Toast.makeText(MainActivity.this, filterGroupModel.toString(), Toast.LENGTH_SHORT).show();
                binding.filterLayout.dismissFilterLayout();
            }
        });
        sortFilterVH.initData(sortFilterData);
        //户型
        MultiFilterVH roomFilterVH = new MultiFilterVH(this);
        FilterGroupModel roomFilterData = FilterModelConvertUtil.convertToFilterGroupModel(filterComplexConditionModel.getRoomType());
        roomFilterData.getItems().add(0, new FilterIdNameModel(FilterIdNameModel.NO_LIMIT_ITEM_ID, "不限"));
        roomFilterVH.setFilterSureCallback(new OnFilterListener<FilterGroupModel>() {
            @Override
            public void onFilter(FilterGroupModel filterGroupModel) {
                Toast.makeText(MainActivity.this, filterGroupModel.toString(), Toast.LENGTH_SHORT).show();
                binding.filterLayout.dismissFilterLayout();
            }
        });
        roomFilterVH.initData(roomFilterData);
        //面积
        MultiFilterVH areaFilterVH = new MultiFilterVH(this);
        FilterGroupModel areaFilterData = FilterModelConvertUtil.convertToFilterGroupModel(filterComplexConditionModel.getArea());
        areaFilterData.getItems().add(0, new FilterIdNameModel(FilterIdNameModel.NO_LIMIT_ITEM_ID, "不限"));
        areaFilterVH.setFilterSureCallback(new OnFilterListener<FilterGroupModel>() {
            @Override
            public void onFilter(FilterGroupModel filterGroupModel) {
                Toast.makeText(MainActivity.this, filterGroupModel.toString(), Toast.LENGTH_SHORT).show();
                binding.filterLayout.dismissFilterLayout();
            }
        });
        areaFilterVH.initData(areaFilterData);

        //添加到tab中
        ArrayList tabList = new ArrayList();
        tabList.add(priceFilterVH);
        tabList.add(sortFilterVH);
        tabList.add(roomFilterVH);
        tabList.add(areaFilterVH);
        //
        binding.filterLayout.initTab(tabList);

    }


}
