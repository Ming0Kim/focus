package com.bb.focus.api.service;

import java.util.Map;
import javax.mail.MessagingException;

public interface MailService {

  public void sendGeneralMail(String from, String to, String subject, String content) throws MessagingException;

  public void sendAccountMail(String to, Map<String, String> content) throws MessagingException;

}
