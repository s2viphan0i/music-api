package com.sinnguyen.service;

import com.sinnguyen.model.UserDTO;

public interface MailService {
	void sendWelcomeMail(UserDTO userDTO);
	void sendForgotMail(UserDTO userDTO);
}
