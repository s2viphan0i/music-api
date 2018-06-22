package com.sinnguyen.dao;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.SearchDTO;

public interface UserDao {
	ResponseModel add(User user);
	ResponseModel edit(User user);
	ResponseModel delete(User user);
	ResponseModel getById(int id);
	ResponseModel search(SearchDTO searchDTO);
	User getUserbyEmail(String email);
}
