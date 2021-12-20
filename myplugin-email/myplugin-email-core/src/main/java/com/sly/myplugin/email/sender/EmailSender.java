package com.sly.myplugin.email.sender;

import com.sly.myplugin.base.result.Result;
import com.sly.myplugin.email.model.MailInfo;
import com.sly.myplugin.email.properties.EmailSenderProperties;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceCompositeResolver;
import org.apache.commons.mail.resolver.DataSourceFileResolver;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;


/**
 * 邮件发送类
 *
 * @author SLY
 * @date 2021/12/7
 */
public class EmailSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private EmailSenderProperties emailSenderProperties;

    public void setEmailSenderProperties(EmailSenderProperties emailSenderProperties) {
        this.emailSenderProperties = emailSenderProperties;
    }

    /**
     * 发送简单邮件（可以发送文字）
     *
     * @param mailInfo 邮件信息
     * @return {@link Result}
     * @author SLY
     * @date 2021/12/7
     */
    public Result<?> sendSimpleEmail(MailInfo mailInfo) {
        try {
            SimpleEmail email = new SimpleEmail();
            // 配置信息
            email.setHostName(emailSenderProperties.getMailServerHost());
            email.setSmtpPort(emailSenderProperties.getMailServerPort());
            email.setFrom(emailSenderProperties.getMailSenderAddress(), emailSenderProperties.getMailSenderNick());
            email.setAuthentication(emailSenderProperties.getMailSenderUsername(), emailSenderProperties.getMailSenderPassword());
            email.setCharset(emailSenderProperties.getCharset());
            email.setSSLOnConnect(emailSenderProperties.isSsl());
            email.setStartTLSEnabled(emailSenderProperties.isTls());

            // 邮件主题
            email.setSubject(mailInfo.getSubject());
            // 邮件内容
            email.setMsg(mailInfo.getContent());

            // 收件人
            for (String addressee : mailInfo.getAddresseeList()) {
                email.addTo(addressee);
            }

            // 抄送人
            for (String cc : mailInfo.getCcList()) {
                email.addCc(cc);
            }

            // 密送人
            for (String bcc : mailInfo.getBccList()) {
                email.addBcc(bcc);
            }

            String send = email.send();

            return Result.success(send);
        } catch (Exception e) {
            LOGGER.error("邮件发送失败：", e);
            throw new RuntimeException("邮件发送失败：", e);
        }
    }

    /**
     * 多元邮件（可以发送文字和附件）
     *
     * @param mailInfo 邮件信息
     * @return {@link Result}
     * @author SLY
     * @date 2021/12/7
     */
    public Result<?> sendMultiPartEmail(MailInfo mailInfo) {
        try {
            MultiPartEmail email = new MultiPartEmail();
            // 配置信息
            email.setHostName(emailSenderProperties.getMailServerHost());
            email.setSmtpPort(emailSenderProperties.getMailServerPort());
            email.setFrom(emailSenderProperties.getMailSenderAddress(), emailSenderProperties.getMailSenderNick());
            email.setAuthentication(emailSenderProperties.getMailSenderUsername(), emailSenderProperties.getMailSenderPassword());
            email.setCharset(emailSenderProperties.getCharset());
            email.setSSLOnConnect(emailSenderProperties.isSsl());
            email.setStartTLSEnabled(emailSenderProperties.isTls());

            // 邮件主题
            email.setSubject(mailInfo.getSubject());
            // 邮件内容
            email.setMsg(mailInfo.getContent());

            // 收件人
            for (String addressee : mailInfo.getAddresseeList()) {
                email.addTo(addressee);
            }

            // 抄送人
            for (String cc : mailInfo.getCcList()) {
                email.addCc(cc);
            }

            // 密送人
            for (String bcc : mailInfo.getBccList()) {
                email.addBcc(bcc);
            }

            // 附件
            for (EmailAttachment attachment : mailInfo.getAttachments()) {
                email.attach(attachment);
            }

            String send = email.send();

            return Result.success(send);
        } catch (Exception e) {
            LOGGER.error("邮件发送失败：", e);
            throw new RuntimeException("邮件发送失败：", e);
        }
    }

    /**
     * 发送html格式邮件（可以发送文字和附件）
     *
     * @param mailInfo 邮件信息
     * @return {@link Result}
     * @author SLY
     * @date 2021/12/7
     */
    public Result<?> sendHtmlEmail(MailInfo mailInfo) {
        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setHostName(emailSenderProperties.getMailServerHost());
            email.setSmtpPort(emailSenderProperties.getMailServerPort());
            email.setFrom(emailSenderProperties.getMailSenderAddress(), emailSenderProperties.getMailSenderNick());
            email.setAuthentication(emailSenderProperties.getMailSenderUsername(), emailSenderProperties.getMailSenderPassword());
            email.setCharset(emailSenderProperties.getCharset());
            email.setSSLOnConnect(emailSenderProperties.isSsl());
            email.setStartTLSEnabled(emailSenderProperties.isTls());

            // 邮件主题
            email.setSubject(mailInfo.getSubject());
            // 邮件内容
            email.setHtmlMsg(mailInfo.getContent());

            // 收件人
            for (String addressee : mailInfo.getAddresseeList()) {
                email.addTo(addressee);
            }

            // 抄送人
            for (String cc : mailInfo.getCcList()) {
                email.addCc(cc);
            }

            // 密送人
            for (String bcc : mailInfo.getBccList()) {
                email.addBcc(bcc);
            }

            // 附件
            for (EmailAttachment attachment : mailInfo.getAttachments()) {
                email.attach(attachment);
            }

            String send = email.send();

            return Result.success(send);
        } catch (Exception e) {
            LOGGER.error("邮件发送失败：", e);
            throw new RuntimeException("邮件发送失败：", e);
        }
    }

    /**
     * 发送带图片的HTML格式邮件（可以发送文字、图片和附件）
     *
     * @param mailInfo 邮件信息
     * @return {@link Result}
     * @author SLY
     * @date 2021/12/7
     */
    public Result<?> sendImageHtmlEmail(MailInfo mailInfo) {
        try {
            ImageHtmlEmail email = new ImageHtmlEmail();
            // 配置信息
            email.setHostName(emailSenderProperties.getMailServerHost());
            email.setSmtpPort(emailSenderProperties.getMailServerPort());
            email.setFrom(emailSenderProperties.getMailSenderAddress(), emailSenderProperties.getMailSenderNick());
            email.setAuthentication(emailSenderProperties.getMailSenderUsername(), emailSenderProperties.getMailSenderPassword());
            email.setCharset(emailSenderProperties.getCharset());
            email.setSSLOnConnect(emailSenderProperties.isSsl());
            email.setStartTLSEnabled(emailSenderProperties.isTls());

            //添加DataSourceFileResolver用于解析本地图片
            DataSourceResolver[] dataSourceResolvers = new DataSourceResolver[]{
                    new DataSourceFileResolver(),
                    new DataSourceUrlResolver(new URL("http://"))
            };

            email.setDataSourceResolver(new DataSourceCompositeResolver(dataSourceResolvers));

            // 邮件主题
            email.setSubject(mailInfo.getSubject());
            // 邮件内容
            email.setHtmlMsg(mailInfo.getContent());

            // 收件人
            for (String addressee : mailInfo.getAddresseeList()) {
                email.addTo(addressee);
            }

            // 抄送人
            for (String cc : mailInfo.getCcList()) {
                email.addCc(cc);
            }

            // 密送人
            for (String bcc : mailInfo.getBccList()) {
                email.addBcc(bcc);
            }

            // 附件
            for (EmailAttachment attachment : mailInfo.getAttachments()) {
                email.attach(attachment);
            }

            String send = email.send();

            return Result.success(send);
        } catch (Exception e) {
            LOGGER.error("邮件发送失败：", e);
            throw new RuntimeException("邮件发送失败：", e);
        }
    }
}
