package com.sinnguyen.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mysql.cj.api.jdbc.Statement;
import com.sinnguyen.dao.UserDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.SearchDTO;
import com.sinnguyen.model.UserMapper;
import com.sinnguyen.util.MainUtility;
import com.sinnguyen.util.PasswordGenerator;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean checkUsername(User user) {
		String sql = "SELECT EXISTS (SELECT 1 FROM user WHERE username = '" + user.getUsername() + "' OR email = '"
				+ user.getEmail() + "')";
		if (this.jdbcTemplate.queryForObject(sql, Integer.class) == 1) {
			return false;
		}
		return true;
	}

	public boolean add(final User user) {
		try {
			final String sql = "INSERT INTO user (username, password, fullname, birthdate, email, phone, activated, role, note) VALUES (?,?,?,?,?,?,?,?)";
			KeyHolder holder = new GeneratedKeyHolder();
			int row = this.jdbcTemplate.update(new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getUsername());
					ps.setString(2, PasswordGenerator.genPassword(user.getPassword()));
					ps.setString(3, user.getFullname());
					ps.setString(4, MainUtility.dateToStringFormat(user.getBirthdate(), "yyyy-MM-dd HH:mm:ss"));
					ps.setString(5, user.getEmail());
					ps.setString(6, user.getPhone());
					ps.setBoolean(7, false);
					ps.setString(8, "ROLE_USER");
					ps.setString(9, user.getNote());

					return ps;
				}
			}, holder);
			if (row > 0) {
				user.setId(holder.getKey().intValue());
				return true;
			}
		} catch (Exception ex) {

		}
		return false;
	}

	public boolean insertActivation(User user) {
		try {
			String sql = "UPDATE user SET code = ? WHERE id = ? AND activated = ?";
			String code = user.getId() + System.currentTimeMillis() + "";
			Object[] newObj = new Object[] { code, user.getId(), false };
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
			Object[] newObj = new Object[] { true, code };
			int row = this.jdbcTemplate.update(sql, newObj);
			if (row > 0) {
				return true;
			}
		} catch (Exception ex) {
		}
		return false;
	}

	public boolean edit(User user) {
		// TODO
		return false;
	}

	public boolean delete(User user) {
		// TODO
		return false;
	}

	public boolean getById(int id) {
		// TODO
		return false;
	}

	public boolean search(SearchDTO searchDTO) {
		// TODO
		return false;
	}

	public User getUserbyEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
		try {
			Object queryForObject = this.jdbcTemplate.queryForObject(sql, new Object[] { email }, new UserMapper());
			return (User) queryForObject;
		} catch (Exception e) {
			return null;
		}
	}

	public User getUserbyUsername(String username) {
		String sql = "SELECT * FROM user WHERE username = ?";
		try {
			Object queryForObject = this.jdbcTemplate.queryForObject(sql, new Object[] { username }, new UserMapper());
			return (User) queryForObject;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean changePassword(User user) {
		try {
			String sql = "UPDATE user SET password = ? WHERE id = ?";
			Object[] newObj = new Object[] { PasswordGenerator.genPassword(user.getPassword()), user.getId() };
			int row = this.jdbcTemplate.update(sql, newObj);
			if (row > 0) {
				return true;
			}
		} catch (Exception ex) {
			
		}
		return false;
	}

}
