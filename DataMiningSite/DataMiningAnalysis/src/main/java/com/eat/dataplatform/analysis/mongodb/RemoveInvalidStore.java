package com.eat.dataplatform.analysis.mongodb;

import com.eat.dataplatform.analysis.entity.ResInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoveInvalidStore extends RichFilterFunction<ResInfo> {
    static Logger logger = LoggerFactory.getLogger(RemoveInvalidStore.class);
    @Override
    public boolean filter(ResInfo resInfo) throws Exception {
        if(resInfo.getResHashCode()==0||StringUtils.isEmpty(resInfo.getResName())){
            return false;
        }
        logger.info("符合规则不为0或者商店名称不为空的信息:{}",resInfo.toString());
        return true;
    }
}
