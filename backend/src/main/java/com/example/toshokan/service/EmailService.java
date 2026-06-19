package com.example.toshokan.service;

import java.time.LocalDate;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service

public class EmailService {
	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendBorrowNotification(String toEmail, String userName, String bookTitle, LocalDate dueDate) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("【図書館アプリ】本の貸出完了のお知らせ");

		String text = String.format(
				"%s 様\n\n" + "いつも図書館アプリをご利用いただきありがとうございます。\n" + "以下の本の貸出処理が完了しました。\n\n"
						+ "----------------------------------------\n" + "■ 本のタイトル: %s\n" + "■ 返却期限日  : %s\n"
						+ "----------------------------------------\n\n" + "期限内のご返却をお願いいたします。",
				userName, bookTitle, dueDate.toString());
		message.setText(text); // メッセージに本文をセット
		mailSender.send(message);
	}

}
