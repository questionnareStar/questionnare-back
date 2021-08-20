package com.question.utils;

import cn.hutool.extra.mail.MailAccount;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/20 23:20
 */
public class MailUtil {

    /**
     * 发送邮箱
     *
     * @param address 邮箱地址
     * @param title   标题
     * @param content 内容
     */
    public void send(String address, String title, String content) {
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(587);
        account.setAuth(true);
        account.setFrom("1444046055@qq.com");
        account.setUser("1444046055@qq.com");
        account.setPass("keafbwxbzhmvgbag");
        cn.hutool.extra.mail.MailUtil.send(account, address, title, content, true);
    }

    /**
     * 发送邮箱验证码
     *
     * @param address 邮箱地址
     * @param code    验证码
     */
    public void sendCode(String address, String code) {
        MailAccount account = new MailAccount();
        account.setHost("smtp.qq.com");
        account.setPort(587);
        account.setAuth(true);
        account.setFrom("1444046055@qq.com");
        account.setUser("1444046055@qq.com");
        account.setPass("keafbwxbzhmvgbag");
        cn.hutool.extra.mail.MailUtil.send(account, address, "问卷星球-验证码", "<h4>您的验证码为：</h4> <h3>" + code + "</h3>，", true);
    }

}
