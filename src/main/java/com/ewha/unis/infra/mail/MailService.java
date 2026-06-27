package com.ewha.unis.infra.mail;

import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendVerificationCode(String toEmail, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject("[UNIS] 이메일 인증번호");
            helper.setText(buildEmailContent(code), true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }

    private String buildEmailContent(String code) {
        return """
                <div style="font-family: Arial, sans-serif; max-width: 480px; margin: 0 auto;">
                                    <h2 style="color: #4A90E2;">UNIS 이메일 인증</h2>
                                    <p>아래 인증번호를 입력해주세요.</p>
                                    <div style="font-size: 32px; font-weight: bold; letter-spacing: 8px;
                                                padding: 16px; background: #f5f5f5; text-align: center;">
                                        %s
                                    </div>
                                    <p style="color: #888; font-size: 12px;">인증번호는 5분간 유효합니다.</p>
                                </div>
                """.formatted(code);
    }
}
