package com.sinnguyen.service;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;

public interface AuthService {
	ResponseModel login(User user);
	ResponseModel logout(String token);
	ResponseModel register(User user);
}
