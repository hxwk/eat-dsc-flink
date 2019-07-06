package com.eat.dataplatform.analysis.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @ClassName PropertiesUtil
 * @Description 配置文件
 * @Author KANGJIAN
 * @Date 2019/3/27 9:58
 **/
public class PropertiesUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
    public static final String fileName = "biz.conf";

    public static PropertiesConfiguration cfg = null;

    static {
        try {
            cfg = new PropertiesConfiguration(fileName);
        } catch (ConfigurationException e) {
            logger.warn("读取配置文件出错:{}", e.getMessage());
        }
        // 当文件的内容发生改变时，配置对象也会刷新
        cfg.setReloadingStrategy(new FileChangedReloadingStrategy());
    }

   /* private PropertiesUtil() {
    }

    public static PropertiesUtil getInstance() {
        if (propertiesUtil == null) {
            return new PropertiesUtil();
        } else {
            return propertiesUtil;
        }
    }*/

    // 读String
    public static String getStringValue(String key) {
        return cfg.getString(key);
    }

    // 读int
    public static int getIntValue(String key) {
        return cfg.getInt(key);
    }

    // 读boolean
    public static boolean getBooleanValue(String key) {
        return cfg.getBoolean(key);
    }

    // 读List
    public static List<?> getListValue(String key) {
        return cfg.getList(key);
    }

    // 读数组
    public static String[] getArrayValue(String key) {
        return cfg.getStringArray(key);
    }

    /*public static void main(String[] args) {
        String host = cfg.getString("mongodb.host");
        String username = cfg.getString("mongodb.username");
    }*/
}
