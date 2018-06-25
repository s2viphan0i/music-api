package com.sinnguyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinnguyen.model.ForgotDTO;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.UserDTO;
import com.sinnguyen.service.ForgotService;
import com.sinnguyen.service.MailService;
import com.sinnguyen.service.UserService;

@RestController
@RequestMapping("/")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ForgotService forgotService;
	
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseModel login() {
		SecurityContext context = SecurityContextHolder.getContext();
		String username = context.getAuthentication().getName();
		
		return userService.getByUsername(username);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseModel register(@RequestBody UserDTO user) {
		ResponseModel result = userService.add(user);
		if (result.isSuccess()) {
			mailService.sendWelcomeMail((UserDTO) result.getContent());
		}
		return result;
	}

	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ResponseModel activate(@RequestParam String code) {
		return userService.activate(code);
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseModel forgot(@RequestBody UserDTO user) {
		ResponseModel result = forgotService.forgot(user);
		if (result.isSuccess()) {
			ForgotDTO f = (ForgotDTO) result.getContent();
			mailService.sendForgotMail(f);
		}
		result.setContent(null);
		return result;
	}
	
	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ResponseModel resetPasword(@RequestBody ForgotDTO forgot) {
		return forgotService.resetPassword(forgot);
	}
}
