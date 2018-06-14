package com.sinnguyen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinnguyen.dao.UserDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	public ResponseModel add(User user) {
		 return userDao.add(user);
	}

	public ResponseModel edit(User user) {
		return userDao.edit(user);
	}

	public ResponseModel delete(User user) {
		return userDao.delete(user);

	}

	public ResponseModel getById(int id) {
		return userDao.getById(id);
	}
}
