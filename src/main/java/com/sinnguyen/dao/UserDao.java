package com.sinnguyen.dao;

import java.util.List;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.SearchDTO;

public interface UserDao {
	void add(User user);
	void edit(User user);
	void delete(User user);
	User getById(int id);
	List<User> search(SearchDTO searchDTO);
}
