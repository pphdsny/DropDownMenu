package com.pphdsny.app.menu.data;

import android.support.v4.util.ArrayMap;

import com.pphdsny.app.menu.MyApplication;
import com.pphdsny.lib.cache.protocal.ECache;
import com.pphdsny.lib.cache.protocal.ICacheParams;

import java.util.Map;

/**
 * Created by wangpeng on 2018/6/29.
 */
public class FilterCacheParam implements ICacheParams<FilterComplexConditionModel> {

    public static final String PARAM_CITY_ID = "PARAM_CITY_ID";

    private int cityId;

    public FilterCacheParam(int cityId) {
        this.cityId = cityId;
    }

    @Override
    public Map getParams() {
        ArrayMap arrayMap = new ArrayMap<>();
        //map中内存cache的key，如没有设置会以CACHE_KEY作为key
        arrayMap.put(ECache.CACHE_KEY, "complex_filter_" + cityId);
        //map中本地cache的key，如没有设置会以CACHE_KEY作为key
        arrayMap.put(ECache.CACHE_KEY, "complex_filter_" + cityId);
        //map中资源cache的key，如没有设置会以CACHE_KEY作为key
        arrayMap.put(ECache.ASSET_KEY, "filter.json");
        //Context实例，最好是Application，本地SP和Asset中会用到
        arrayMap.put(ECache.CONTEXT_KEY, MyApplication.sContext);
        //业务传参
        arrayMap.put(PARAM_CITY_ID, cityId);
        return arrayMap;
    }

    @Override
    public Class<FilterComplexConditionModel> getDataClass() {
        return FilterComplexConditionModel.class;
    }

    @Override
    public long getExpityTime() {
        return 0;
    }
}
