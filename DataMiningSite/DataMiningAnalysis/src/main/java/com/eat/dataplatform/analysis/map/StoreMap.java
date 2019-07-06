package com.eat.dataplatform.analysis.map;

import com.eat.dataplatform.analysis.entity.StoreInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @ClassName StoreMap
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/3/28 10:10
 **/
public class StoreMap implements MapFunction<String, StoreInfo> {
    @Override
    public StoreInfo map(String value) throws Exception {
        if(StringUtils.isBlank(value)){
            return null;
        }
        String[] storeInfo = value.split(";",-1);
        String id = storeInfo[0];
        String area = storeInfo[1];
        String avgPrice = storeInfo[2];
        String cate = storeInfo[3];
        String createTime = storeInfo[4];
        String createTimestamp = storeInfo[5];
        String distance = storeInfo[6];
        String latitude = storeInfo[7];
        String longitude = storeInfo[8];
        String ratingText = storeInfo[9];
        String resHashCode = storeInfo[10];
        String resName=storeInfo[11];
        String timestamp=storeInfo[12];
        String voucherInfo=storeInfo[13];

        /*String tablename = "userflaginfo";
        String rowkey = userid;
        String famliyname = "baseinfo";
        String colum = "carrierinfo";//运营商
        HbaseUtils.putdata(tablename,rowkey,famliyname,colum,carriertypestring);*/
        StoreInfo storeInfo1 = new StoreInfo();
        storeInfo1.setCount(1L);
        storeInfo1.setStore(resName);
        storeInfo1.setGroupfield(cate);
        return storeInfo1;
    }
}
