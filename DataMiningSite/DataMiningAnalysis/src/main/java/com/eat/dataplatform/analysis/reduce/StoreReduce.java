package com.eat.dataplatform.analysis.reduce;

import com.eat.dataplatform.analysis.entity.StoreInfo;
import org.apache.flink.api.common.functions.RichReduceFunction;

/**
 * @ClassName StoreReduce
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/3/28 10:12
 **/
public class StoreReduce extends RichReduceFunction<StoreInfo> {
    @Override
    public StoreInfo reduce(StoreInfo storeInfo, StoreInfo s1) throws Exception {
        String store = storeInfo.getStore();
        Long count1 = storeInfo.getCount();
        Long count2 = s1.getCount();

        StoreInfo storeInfoFinal = new StoreInfo();
        storeInfoFinal.setStore(store);
        storeInfoFinal.setCount(count1+count2);
        storeInfoFinal.setGroupfield(storeInfo.getGroupfield());
        return storeInfoFinal;
    }
}
