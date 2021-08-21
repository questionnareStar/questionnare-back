package com.question.data;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

/**
 * 缓存数据中心，验证码相关
 *
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/20 23:26
 */
public class CodeData {

    /**
     * 用户登录，邮箱验证码
     * 默认保存5分钟
     */
    public static TimedCache<String, String> mailLoginCode = CacheUtil.newTimedCache(300000);

    /**
     * 用户注册，邮箱验证码
     * 默认保存5分钟
     */
    public static TimedCache<String, String> mailRegisterCode = CacheUtil.newTimedCache(300000);

}
