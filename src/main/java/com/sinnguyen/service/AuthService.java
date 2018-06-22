package com.sinnguyen.service;

import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.UserDTO;

public interface AuthService {
	ResponseModel login(UserDTO user);
	ResponseModel logout(String token);
	ResponseModel register(UserDTO user);
	ResponseModel activate(String code);
}
