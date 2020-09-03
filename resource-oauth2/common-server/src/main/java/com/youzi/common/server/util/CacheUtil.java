package com.youzi.common.server.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.youzi.common.server.config.RedisBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class CacheUtil {

    private static CacheUtil cacheUtil;
    /**
     * dao
     */
    @Autowired
    private RedisBaseDao dao;

    /**
     * @name 中文名称
     * @description 相关说明
     * @time 创建时间:2019年2月27日下午9:25:58
     * @author 史雪涛
     * @history 修订历史（历次修订内容、修订人、修订时间等）
     *
     */
    @PostConstruct
    public void init() {
        cacheUtil = this;
        cacheUtil.dao = this.dao;
    }

    /**
     * @Description: 获取MAP类型数据
     * @author: yaoyuan
     * @date:  2020/8/12 11:14
     * @version V1.0
     */
    public static Map<String, Object> getMapCacheValue(String tableName, String dmz) throws Exception {
        Map<String, Object> resMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(dmz)) {
            return resMap;
        }
        final String redisKey = "Map:" + tableName.toUpperCase() + ":" + dmz;
        String value = cacheUtil.dao.getString(redisKey);
        resMap = JSON.parseObject(value,HashMap.class);
        return resMap;
    }

    /**
     * @Description: 获取String类型数据
     * @author: yaoyuan
     * @date:  2020/8/12 11:16
     * @version V1.0
     */
    public static String getStringCacheValue(String tableName, String dmz, String key) throws Exception {
        String value = "";
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(dmz) || StringUtils.isEmpty(key)) {
            return value;
        }
        final String redisKey = "String:" + tableName.toUpperCase() + ":" + key + "&&" + dmz;
        value = cacheUtil.dao.getString(redisKey);
        return value;
    }

    /**
     * @Description: 获取List类型数据
     * @author: yaoyuan
     * @date:  2020/8/12 11:17
     * @version V1.0
     */
    public static List<Map<String, Object>> getListCacheValue(String tableName) throws Exception {
        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        if (StringUtils.isEmpty(tableName)) {
            return resList;
        }
        final String redisKey = "List:" + tableName.toUpperCase() + ":all";
        final String value = cacheUtil.dao.getString(redisKey);
        String str[] = value.split(",");
        final List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        final JSONArray jsonArray = JSONArray.parseArray(value);
        JSONObject jsonObject = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            paramList.add(jsonObject.toJavaObject(Map.class));
        }
        return paramList;
    }

    /**
     * @Description: 新增
     * @author: yaoyuan
     * @date:  2020/8/12 11:22
     * @version V1.0
     */
    public static void setString(String key, String value) throws Exception {
        cacheUtil.dao.setString(key, value);
    }

    /**
     * @Description: 删除
     * @author: yaoyuan
     * @date:  2020/8/12 11:24
     * @version V1.0
     */
    public static void deleteKey(String... keys) throws Exception {
        cacheUtil.dao.deleteKey(keys);
    }

    /**
     * @Description: 设置过期时间
     * @author: yaoyuan
     * @date:  2020/9/3 8:57
     * @version V1.0
     */
    public static void expire(String key, long millisecondsTimestamp) throws Exception {
        cacheUtil.dao.expire(key, millisecondsTimestamp);
    }
}
