package com.sinnguyen.dao;

import com.sinnguyen.entities.User;

public interface AuthDao {
	User login(User user);
	boolean checkUsername(User user);
	boolean register(User user);
	boolean insertActivation(User user);
	boolean activate(String code);
}
