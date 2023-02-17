package com.bb.focus.api.service;

import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

  private final JavaMailSender javaMailSender;

  public void sendGeneralMail(String from, String to, String subject, String content) throws MessagingException {
    StringBuilder mailContent = new StringBuilder();
    mailContent.append("<html><body><div>")
            .append(content)
            .append("</div></body></html>");

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

    mimeMessageHelper.setFrom(from);
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(mailContent.toString(), true);

    javaMailSender.send(message);
  }

  public void sendAccountMail(String to, Map<String, String> content) throws MessagingException {

    StringBuilder mailContent = new StringBuilder();
    mailContent.append("<html><body><h2>계정 안내</h2><div>안녕하세요. BlackBunny 주식회사 입니다.<br>귀하의 계정 아이디는 ")
        .append(content.get("id"))
        .append("이며, 비밀번호는 ")
        .append(content.get("pwd"))
        .append("입니다.<br>감사합니다.</div></body></html>");

    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

    mimeMessageHelper.setFrom("blackbunnya106@gmail.com");
    mimeMessageHelper.setTo(to);
    mimeMessageHelper.setSubject("[BlackBunny Inc] 계정 정보 안내");
    mimeMessageHelper.setText(mailContent.toString(), true);

    javaMailSender.send(message);
  }
}
