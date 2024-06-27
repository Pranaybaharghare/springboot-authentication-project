package com.example.service;

import jakarta.mail.MessagingException;

public interface MailService {

	void sendMailWIthToken(String email, String link) throws MessagingException;
}
