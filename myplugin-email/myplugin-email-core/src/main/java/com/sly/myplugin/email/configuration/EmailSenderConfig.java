package com.sly.myplugin.email.configuration;

import com.sly.myplugin.email.properties.EmailSenderProperties;
import com.sly.myplugin.email.sender.EmailSender;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 邮件发送配置类
 *
 * @author SLY
 * @date 2021/12/7
 */
@Configuration
@EnableConfigurationProperties(EmailSenderProperties.class)
public class EmailSenderConfig {

	/**
	 * 邮件注册验证
	 *
	 * @param emailSenderProperties 邮件配置
	 * @author SLY
	 * @date 2021/12/7
	 * @return {@link EmailSender}
	 */
	@Bean
	public EmailSender getEmailSender(EmailSenderProperties emailSenderProperties) {
		EmailSender emailSender = new EmailSender();
		emailSender.setEmailSenderProperties(emailSenderProperties);
		return emailSender; 
	}
}
