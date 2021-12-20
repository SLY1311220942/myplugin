package com.sly.myplugin.email.annotation;

import com.sly.myplugin.email.configuration.EmailSenderConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启邮件发送配置注解
 *
 * @author SLY
 * @date 2021/12/7
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EmailSenderConfig.class)
public @interface EnableEmailSender {

}
