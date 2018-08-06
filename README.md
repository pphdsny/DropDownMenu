# 构建高复用的搜索筛选项（实现篇）

## 前言

一些的实现都是来自于需求，详见前文[构建高复用的搜索筛选项（前景篇）](https://www.jianshu.com/p/d4871d21b261)。

## 实现类图

![image.png](https://upload-images.jianshu.io/upload_images/2014593-01c67e96bd6d599b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 快速入门

>   **特别说明：** 筛选的数据来源基于 [构建灵活的缓存机制](https://www.jianshu.com/p/a9ae44869294) 实现。

### Gradle依赖

```shell
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    implementation 'com.github.pphdsny:DropDownMenu:1.0.1'
}
```

### 获取数据

```java 
    private void initViewData() {
        //缓存策略，默认从列表中按序读取
        DefaultCacheStrategy<FilterComplexConditionModel> cacheStrategy = new DefaultCacheStrategy<>(
                new MemoryCache<FilterComplexConditionModel>(),
                new LocalCache<FilterComplexConditionModel>(),
                new AssetCache<FilterComplexConditionModel>()
        );
        //数据工厂
        final CacheFactory<FilterComplexConditionModel> cacheFactory = new CacheFactory<>(cacheStrategy);
        //筛选参数
        final FilterCacheParam filterCacheParam = new FilterCacheParam(1002);
        //获取数据
        cacheFactory.getData(filterCacheParam)
                .subscribe(new Action1<FilterComplexConditionModel>() {
                    @Override
                    public void call(FilterComplexConditionModel filterComplexConditionModel) {
                        //将数据保存起来
                        cacheFactory.saveData(filterCacheParam,filterComplexConditionModel);
                        //填充tab
                        setTabData(filterComplexConditionModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "没有有效数据", Toast.LENGTH_SHORT).show();
                    }
                });
    }
```

### 填充tabView

```java
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
        //添加到View中
        binding.filterLayout.initTab(tabList);
    }
```

## 关键类分析

### IFilterSelect

>   定义了选中的操作。

```java
public interface IFilterSelect<T> {

    /**
     * 设置选中回调
     * @param filterSureCallback
     */
    void setFilterSureCallback(OnFilterListener<T> filterSureCallback);

    /**
     * 获取选中数据
     * @return
     */
    T getSelectFilterData();
}
```

### ITabFilter

>   定义了每项筛选项所需的操作，`getFilterView`是具体筛选项View，包含`IItemFilter`子项的实现。`getTabView`是筛选tab操作的View。

```java
/**
 * 筛选项tab
 * @param <T> 入参数据类型
 * @param <K> 选中参数数据类型
 */
public interface ITabFilter<T, K> extends IFilterSelect<K> {

    /**
     * 获取筛选View
     * @return
     */
    View getFilterView();

    /**
     * 获取筛选tabView
     * @return
     */
    View getTabView();

    /**
     * 初始化数据
     * @param t
     */
    void initData(T t);

    /**
     * 设置选中项数据
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
     * @return
     */
    boolean isShowing();
}
```

### IITemFilter

>   每个筛选子项所需的操作。

```java
public interface IItemFilter<T> {

    /**
     * 初始化数据
     * @param t
     */
    void initData(T t);

    /**
     * 设置子项选中否
     * @param isSelect
     */
    void setSelect(boolean isSelect);
}
```

### PPFilterLayout

>   对实现了`ITabFilter`的具体筛选项集合进行组装，并封装了筛选操作的UI。

```java
public class PPFilterLayout extends FrameLayout {
    private LayoutFilterBinding binding;
    private Context mContext;

    private List<ITabFilter> tabFilters;
    private ITabFilter currentShowFilter;

    public PPFilterLayout(@NonNull Context context) {
        this(context, null);
    }

    public PPFilterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PPFilterLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_filter, this, true);
        binding.flFilterWrap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissFilterLayout();
            }
        });
    }

    /**
     * 初始化tab
     * @param tabs
     */
    public void initTab(List<ITabFilter> tabs) {
        this.tabFilters = tabs;
        if (tabs == null) {
            return;
        }
        for (int i = 0; i < tabs.size(); i++) {
            ITabFilter iTabFilter = tabs.get(i);
            addTab(iTabFilter);
            addFilterView(iTabFilter);
            if (i < tabs.size() - 1) {
                addTabDivideLine();
            }
        }
    }

    public List<ITabFilter> getTabFilters() {
        return tabFilters;
    }

    private void addFilterView(ITabFilter tab) {
        View filterView = tab.getFilterView();
        filterView.setVisibility(GONE);
        binding.flFilterBodyWrap.addView(filterView);
    }

    private void addTabDivideLine() {
        View divideLineView = new View(mContext);
        divideLineView.setBackgroundResource(R.color.tab_bg_color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelOffset(R.dimen.tab_divide_width), mContext.getResources().getDimensionPixelOffset(R.dimen.tab_divide_height));
        params.gravity = Gravity.CENTER_VERTICAL;
        binding.llFilter.addView(divideLineView, params);
    }

    private void addTab(final ITabFilter tab) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        View tabView = tab.getTabView();
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterLayout(tab);
            }
        });
        binding.llFilter.addView(tabView, layoutParams);
    }

    /**
     * 显示筛选项
     * @param tab
     */
    private void showFilterLayout(ITabFilter tab) {
        if (tab == null) return;
        if (currentShowFilter == tab && tab.isShowing()) {
            //重复点击当前tab，显示前应该先隐藏
            dismissFilterLayout();
            return;
        }
        if (currentShowFilter != null) {
            currentShowFilter.dismiss();
        }
        currentShowFilter = tab;
        currentShowFilter.show();
        binding.flFilterWrap.setVisibility(VISIBLE);
    }

    /**
     * 隐藏筛选项
     */
    public void dismissFilterLayout() {
        if (currentShowFilter != null) {
            currentShowFilter.dismiss();
        }
        binding.flFilterWrap.setVisibility(GONE);
    }

    /**
     * 清空筛选数据
     */
    public void cleanFilter() {
        if (tabFilters == null || tabFilters.size() == 0) {
            return;
        }
        for (int i = 0; i < tabFilters.size(); i++) {
            tabFilters.get(i).cleanFilter();
        }
    }

}
```

## 后记

如果你正好想做个类似搜索筛选下拉框，这个方案可供参考。欢迎拍砖~

源码地址：https://github.com/pphdsny/DropDownMenu