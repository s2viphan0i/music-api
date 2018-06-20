package com.sinnguyen.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinnguyen.dao.AuthDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.UserMapper;
import com.sinnguyen.util.MainUtility;

@Repository
public class AuthDaoImpl implements AuthDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public User login(User user) {
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		User result = new User();
		try {
			Object queryForObject = this.jdbcTemplate.queryForObject(sql,
					new Object[] { user.getUsername(), MainUtility.MD5(user.getPassword()) }, new UserMapper());
			result = (User) queryForObject;
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean checkUsername(User user) {
		String sql = "SELECT EXISTS (SELECT 1 FROM user WHERE username = '" + user.getUsername() + "' OR email = '" + user.getEmail() + "')";
		if (this.jdbcTemplate.queryForObject(sql, Integer.class) == 1) {
			return false;
		}
		return true;
	}

	public boolean register(User user) {
		try {
			String sql = "INSERT INTO user (username, password, fullname, birthdate, email, phone, activated, note) VALUES (?,?,?,?,?,?,?)";
			Object[] newObj = new Object[] { user.getUsername(), MainUtility.MD5(user.getPassword()),
					user.getFullname(), MainUtility.dateToStringFormat(user.getBirthdate(), "yyyy-MM-dd HH:mm:ss"),
					user.getEmail(), user.getPhone(), 0, user.getNote() };
			int row = this.jdbcTemplate.update(sql, newObj);

			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
