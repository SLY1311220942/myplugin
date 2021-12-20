package com.sly.myplugin.email.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 邮件发送属性类
 *
 * @author SLY
 * @date 2021/12/7
 */
@ConfigurationProperties(prefix = "plugin.email")
public class EmailSenderProperties {

    /**
     * 邮件服务器主机地址
     */
    private String mailServerHost;
    /**
     * 邮件服务器主机端口
     */
    private int mailServerPort;
    /**
     * 邮件发送者地址
     */
    private String mailSenderAddress;
    /**
     * 邮件发送者昵称
     */
    private String mailSenderNick;
    /**
     * 邮件发送者用户名
     */
    private String mailSenderUsername;
    /**
     * 邮件发送者密码
     */
    private String mailSenderPassword;
    /**
     * 邮件编码格式默认UTF-8
     */
    private String charset = "UTF-8";
    /**
     * 设置SSL加密方式 默认false
     */
    private boolean ssl;
    /**
     * 设置SSL加密方式 默认false
     */
    private boolean tls;

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public int getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(int mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getMailSenderAddress() {
        return mailSenderAddress;
    }

    public void setMailSenderAddress(String mailSenderAddress) {
        this.mailSenderAddress = mailSenderAddress;
    }

    public String getMailSenderNick() {
        return mailSenderNick;
    }

    public void setMailSenderNick(String mailSenderNick) {
        this.mailSenderNick = mailSenderNick;
    }

    public String getMailSenderUsername() {
        return mailSenderUsername;
    }

    public void setMailSenderUsername(String mailSenderUsername) {
        this.mailSenderUsername = mailSenderUsername;
    }

    public String getMailSenderPassword() {
        return mailSenderPassword;
    }

    public void setMailSenderPassword(String mailSenderPassword) {
        this.mailSenderPassword = mailSenderPassword;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public boolean isTls() {
        return tls;
    }

    public void setTls(boolean tls) {
        this.tls = tls;
    }

}
