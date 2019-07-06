package com.eat.dataplatform.analysis.entity;

/**
 * @ClassName StoreInfo
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/3/28 10:09
 **/
public class StoreInfo {
    private String store;//店铺
    private Long count;//数量
    private String groupfield;//分组

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getGroupfield() {
        return groupfield;
    }

    public void setGroupfield(String groupfield) {
        this.groupfield = groupfield;
    }
}
