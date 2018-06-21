package com.sinnguyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.UserDTO;
import com.sinnguyen.service.AuthService;
import com.sinnguyen.service.MailService;

@RestController
@RequestMapping("/")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity login(@RequestBody UserDTO user) {
		String Authorization = "";
		try {
			ResponseModel result = authService.login(user);
			if (result.isSuccess()) {
				Authorization = "Bearer " + ((UserDTO) result.getContent()).getToken();
			}
			HttpHeaders headers = new HttpHeaders();
			ObjectMapper mapper = new ObjectMapper();
			headers.add("Authorization", Authorization);

			return new ResponseEntity(mapper.writeValueAsString(result).toString(), headers, HttpStatus.OK);
		} catch (JsonProcessingException e) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public ResponseModel logout(@RequestHeader("Authorization") String token) {
		return authService.logout(token);
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseModel register(@RequestBody UserDTO user) {
		ResponseModel result = authService.register(user);
		if(result.isSuccess()) {
			mailService.sendWelcomeMail((UserDTO)result.getContent());
		}
		return result;
	}
}
