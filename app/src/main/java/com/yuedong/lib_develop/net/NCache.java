package com.yuedong.lib_develop.net;

/**
 * 网络数据缓存 基类
 */
public abstract class NCache {
    // 缓存时间 默认一天
    protected int cacheTime = 86400000;
    // 保存缓存
    public abstract boolean cacheData(String key,String json);
    // 获取缓存
    public abstract String getData(String key);
    // 检查缓存
    protected abstract void checkCache();
}
