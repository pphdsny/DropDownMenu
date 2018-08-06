package com.pphdsny.app.menu.data;

import com.pphdsny.lib.menu.impl.model.PPModel;

import java.util.List;

/**
 * Created by wangpeng on 2018/08/06.
 * 过滤筛选条件model
 */

public class FilterComplexConditionModel extends PPModel {

    private ConditionModel condition_price;             //总价
    private ConditionModel condition_smartOrder;        //排序
    private ConditionModel condition_area;              //面积
    private ConditionModel condition_roomType;          //房型

    public ConditionModel getRoomType() {
        return condition_roomType;
    }

    public void setRoomType(ConditionModel roomType) {
        this.condition_roomType = roomType;
    }

    public ConditionModel getSmartOrder() {
        return condition_smartOrder;
    }

    public void setSmartOrder(ConditionModel smartOrder) {
        this.condition_smartOrder = smartOrder;
    }

    public ConditionModel getPrice() {
        return condition_price;
    }

    public void setPrice(ConditionModel price) {
        this.condition_price = price;
    }

    public ConditionModel getArea() {
        return condition_area;
    }

    public void setArea(ConditionModel area) {
        this.condition_area = area;
    }

    public static class ConditionModel extends PPModel {

        private String chunkName;
        private List<OptionsModel> options;

        public String getChunkName() {
            return chunkName;
        }

        public void setChunkName(String chunkName) {
            this.chunkName = chunkName;
        }

        public List<OptionsModel> getOptions() {
            return options;
        }

        public void setOptions(List<OptionsModel> options) {
            this.options = options;
        }

        public static class OptionsModel extends PPModel {

            /**
             * code : 1
             * label : 低层
             */
            public OptionsModel(int code, String label) {
                this.code = code;
                this.label = label;
            }

            public OptionsModel() {
            }

            private int code;
            private String label;

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return "OptionsModel{" +
                        "code=" + code +
                        ", label='" + label + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ConditionModel{" +
                    "chunkName='" + chunkName + '\'' +
                    ", options=" + options +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FilterComplexConditionModel{" +
                "condition_price=" + condition_price +
                ", condition_smartOrder=" + condition_smartOrder +
                ", condition_area=" + condition_area +
                ", condition_roomType=" + condition_roomType +
                '}';
    }
}
