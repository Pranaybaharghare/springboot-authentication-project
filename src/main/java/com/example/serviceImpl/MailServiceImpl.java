package com.example.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.example.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendMailWIthToken(String email, String link) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
		
		String subject = "give the subject here";
		String text = "give the text for mail";
		
		helper.setSubject(subject);
		helper.setText(text,true);
		
		
		try {
			javaMailSender.send(message);
		}catch(MailSendException e) {
			throw new MailSendException("Failed to sned mail");
		}
	}

}
