package com.sinnguyen.dao;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.SearchDTO;

public interface UserDao {
	boolean add(User user);
	boolean checkUsername(User user);
	boolean edit(User user);
	boolean delete(User user);
	boolean getById(int id);
	boolean search(SearchDTO searchDTO);
	boolean insertActivation(User user);
	boolean activate(String code);
	User getUserbyEmail(String email);
	User getUserbyUsername(String username);
	boolean changePassword(User user);
}
