package com.sinnguyen.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sinnguyen.entities.User;

public class UserMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		user.setFullname(rs.getString("fullname"));
		user.setPhone(rs.getString("phone"));
		user.setBirthdate(rs.getDate("birthdate"));
		user.setNote(rs.getString("note"));
		return user;
	}

}
