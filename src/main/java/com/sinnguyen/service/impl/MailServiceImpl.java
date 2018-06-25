package com.sinnguyen.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sinnguyen.model.ForgotDTO;
import com.sinnguyen.model.UserDTO;
import com.sinnguyen.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	VelocityEngine velocityEngine;
	
	public void sendWelcomeMail(UserDTO userDTO) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("BBMusic Registration");
			mimeMessageHelper.setFrom("hakleader@gmail.com");
			mimeMessageHelper.setTo(userDTO.getEmail());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("username", userDTO.getUsername());
			model.put("code", userDTO.getCode());
			mimeMessageHelper.setText(getWelcomeContentFromTemplate(model), true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
				
		}
	}
	
	public String getWelcomeContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mailtemplate/welcome.vm", model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	public void sendForgotMail(ForgotDTO forgot) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setSubject("BBMusic Registration");
			mimeMessageHelper.setFrom("hakleader@gmail.com");
			mimeMessageHelper.setTo(forgot.getUser().getEmail());

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("username", forgot.getUser().getUsername());
			model.put("code", forgot.getCode());
			mimeMessageHelper.setText(getForgotContentFromTemplate(model), true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
				
		}
	}
	
	public String getForgotContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mailtemplate/forgot.vm", model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

}
