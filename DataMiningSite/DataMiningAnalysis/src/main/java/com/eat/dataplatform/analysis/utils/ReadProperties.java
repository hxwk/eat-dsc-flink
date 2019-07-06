package com.eat.dataplatform.analysis.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

/**
 * @ClassName ReadProperties
 * @Description 用于获取bin路径下配置信息
 * @Author KANGJIAN
 * @Date 2019/3/28 9:50
 **/
public class ReadProperties {
    public final static Config config = ConfigFactory.load("biz.conf");
    public static String configPath = "/yadata/6_Task/storeSummary/conf/biz.conf";
    public static String masterPath = "/yadata/6_Task/storeSummary/conf/master.conf";

    //public final static Config config = ConfigFactory.parseFile(new File(configPath));

    public static String getKey(String key) {
        return config.getString(key).trim();
    }

    public static String getKey(String key, String filename) {
        Config config = ConfigFactory.load(filename);
        return config.getString(key).trim();
    }
}
