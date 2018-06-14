package com.sinnguyen.service;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;

public interface UserService {
	ResponseModel add(User user);
	ResponseModel edit(User user);
	ResponseModel delete(User user);
	ResponseModel getById(int id);
}
