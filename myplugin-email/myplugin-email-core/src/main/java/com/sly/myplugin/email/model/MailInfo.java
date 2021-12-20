package com.sly.myplugin.email.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.mail.EmailAttachment;

/**
 * 邮件信息 model
 *
 * @author SLY
 * @date 2021/12/7
 */
public class MailInfo {
    /**
     * 收件人
     */
    private Set<String> addresseeList = new LinkedHashSet<>();
    /**
     * 抄送人
     */
    private Set<String> ccList = new LinkedHashSet<>();
    /**
     * 密送人
     */
    private Set<String> bccList = new LinkedHashSet<>();
    /**
     * 附件
     */
    private List<EmailAttachment> attachments = new ArrayList<>();
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件的文本内容
     */
    private String content;

    public Set<String> getAddresseeList() {
        return addresseeList;
    }

    public void setAddresseeList(Set<String> addresseeList) {
        this.addresseeList = addresseeList;
    }

    public void addAddressee(String addressee) {
        addresseeList.add(addressee);
    }

    public Set<String> getCcList() {
        return ccList;
    }

    public void setCcList(Set<String> ccList) {
        this.ccList = ccList;
    }

    public void addCc(String cc) {
        ccList.add(cc);
    }

    public Set<String> getBccList() {
        return bccList;
    }

    public void setBccList(Set<String> bccList) {
        this.bccList = bccList;
    }

    public void addBcc(String bcc) {
        bccList.add(bcc);
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(EmailAttachment attachment) {
        attachments.add(attachment);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
