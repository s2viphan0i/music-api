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
			mimeMessageHelper.setText(geContentFromTemplate(model), true);

			mailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
				
		}
	}
	
	public String geContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/mailtemplate/welcome.vm", model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

}
