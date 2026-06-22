package com.example.toshokan.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    // メール認証
    public void sendVerificationEmail(String email, String name, String verifyUrl) throws ResendException {
        Resend resend = new Resend(apiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(email)
                .subject("【図書館】メールアドレスの確認")
                .html("<p>" + name + " さん</p><p>以下のリンクをタップして会員登録を完了してください。</p><a href='" + verifyUrl + "'>認証する</a>")
                .build();
        resend.emails().send(params);
    }

    // パスワードリセット
    public void sendPasswordResetNotification(String email, String name, String resetUrl) throws ResendException {
        Resend resend = new Resend(apiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(email)
                .subject("【図書館】パスワードリセットのご案内")
                .html("<p>" + name + " さん</p><p>以下のリンクからパスワードを再設定してください。</p><a href='" + resetUrl + "'>リセットする</a>")
                .build();
        resend.emails().send(params);
    }

 // 貸出通知
    public void sendBorrowNotification(String email, String name, String title, LocalDate dueDate) throws ResendException {
        Resend resend = new Resend(apiKey);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev")
                .to(email)
                .subject("【図書館】貸出完了のお知らせ")
                .html("<p>" + name + " さん</p><p>「" + title + "」を借りました。</p><p>返却期限：" + dueDate + "</p>")
                .build();
        resend.emails().send(params);
    }
}