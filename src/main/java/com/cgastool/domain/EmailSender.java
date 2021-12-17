package com.cgastool.domain;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import java.nio.charset.StandardCharsets;

@Component
public class EmailSender {

	@Value("${from_email}")
	private String fromEmail;

	private final JavaMailSender mailSender;

	private final SpringTemplateEngine templateEngine;

	public EmailSender(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		message.setFrom(fromEmail);
		mailSender.send(message);
	}

	public void sendHtmlEmail(Mail mail) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//        helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
		Context context = new Context();
		context.setVariables(mail.getProps());

		String html = templateEngine.process("forgot-password-template", context);
		helper.setTo(mail.getMailTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(fromEmail);
		mailSender.send(message);
	}
}