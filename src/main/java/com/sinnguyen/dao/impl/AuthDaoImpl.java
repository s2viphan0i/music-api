package com.sinnguyen.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.sinnguyen.dao.AuthDao;
import com.sinnguyen.entities.Forgot;
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
		String sql = "SELECT EXISTS (SELECT 1 FROM user WHERE username = '" + user.getUsername() + "' OR email = '"
				+ user.getEmail() + "')";
		if (this.jdbcTemplate.queryForObject(sql, Integer.class) == 1) {
			return false;
		}
		return true;
	}

	public boolean register(final User user) {
		try {
			final String sql = "INSERT INTO user (username, password, fullname, birthdate, email, phone, activated, note) VALUES (?,?,?,?,?,?,?,?)";
			Object[] newObj = new Object[] { user.getUsername(), MainUtility.MD5(user.getPassword()),
					user.getFullname(), MainUtility.dateToStringFormat(user.getBirthdate(), "yyyy-MM-dd HH:mm:ss"),
					user.getEmail(), user.getPhone(), false, user.getNote() };
			KeyHolder holder = new GeneratedKeyHolder();
			int row = this.jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getUsername());
					ps.setString(2, MainUtility.MD5(user.getPassword()));
					ps.setString(3, user.getFullname());
					ps.setString(4, MainUtility.dateToStringFormat(user.getBirthdate(), "yyyy-MM-dd HH:mm:ss"));
					ps.setString(5, user.getEmail());
					ps.setString(6, user.getPhone());
					ps.setBoolean(7, false);
					ps.setString(8, user.getNote());

					return ps;
				}
			}, holder);
			user.setId(holder.getKey().intValue());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean insertActivation(User user) {
		try {
			String sql = "UPDATE user SET code = ? WHERE id = ? AND activated = ?";
			String code = user.getId() + System.currentTimeMillis() + "";
			Object[] newObj = new Object[] { code, user.getId(), false};
			int row = this.jdbcTemplate.update(sql, newObj);
			user.setCode(code);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean activate(String code) {
		try {
			String sql = "UPDATE user SET activated = ? WHERE code = ?";
			Object[] newObj = new Object[] { true, code};
			int row = this.jdbcTemplate.update(sql, newObj);
			if(row>0) {
				return true;
			}
		} catch (Exception ex) {
		}
		return false;
	}
	
	public boolean insertForgot(User user) {
		try {
			String sql = "INSERT INTO forgot (user_id, code, timestamp) VALUES (?,?,?)";
			String timestamp = System.currentTimeMillis() + "";
			String code = UUID.randomUUID().toString().substring(0, 5);
			Object[] newObj = new Object[] { user.getId(), code, timestamp};
			int row = this.jdbcTemplate.update(sql, newObj);
			if(row>0) {
				Forgot forgot = new Forgot();
				forgot.setCode(code);
				forgot.setTimestamp(timestamp);
				user.setForgot(forgot);
				return true;
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
