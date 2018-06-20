package com.sinnguyen.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinnguyen.dao.UserDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.SearchDTO;
import com.sinnguyen.model.UserMapper;
import com.sinnguyen.util.MainUtility;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ResponseModel add(User user) {
		String sqlCheckExist;
		ResponseModel result = new ResponseModel();
		try {
			sqlCheckExist = "SELECT EXISTS (SELECT 1 FROM user WHERE username = '" + user.getUsername() + "')";
			if (this.jdbcTemplate.queryForObject(sqlCheckExist, Integer.class) == 1) {
				result.setSuccess(false);
				result.setMsg("Tên đăng nhập đã tồn tại! Vui lòng nhập lại");
				return result;
			}
			String sql = "INSERT INTO user (username, password, fullname, birthdate, email, phone, note) VALUES (?,?,?,?,?,?,?)";
			Object[] newObj = new Object[] { user.getUsername(), MainUtility.MD5(user.getPassword()),
					user.getFullname(), MainUtility.dateToStringFormat(user.getBirthdate(), "yyyy-MM-dd HH:mm:ss"),
					user.getEmail(), user.getPhone(), user.getNote() };
			int row = this.jdbcTemplate.update(sql, newObj);
			result.setSuccess(true);
			result.setMsg("Đăng ký thành công");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("Có lỗi xảy ra! Vui lòng thử lại sau");
		}
		return result;
	}

	public ResponseModel edit(User user) {
		ResponseModel result = new ResponseModel();
		try {
			String sql = "UPDATE user SET fullname = ?, birthdate = ?, email = ?, phone = ?, note = ?"
					+ " WHERE id = ?";
			Object[] newObj = new Object[] {user.getFullname(), 
					MainUtility.dateToStringFormat(user.getBirthdate(), "yyyy-MM-dd HH:mm:ss"), 
					user.getEmail(), user.getPhone(), user.getNote(), user.getId() };
			int row = this.jdbcTemplate.update(sql, newObj);
			result.setSuccess(true);
			result.setMsg("Cập nhật thông tin thành công");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("Có lỗi xảy ra! Vui lòng thử lại sau");
		}
		return result;
	}

	public ResponseModel delete(User user) {
		ResponseModel result = new ResponseModel();
		try {
			String sql = "DELETE FROM user WHERE id = ?";
			Object[] newObj = new Object[] {user.getId() };
			int row = this.jdbcTemplate.update(sql, newObj);
			result.setSuccess(true);
			result.setMsg("Xóa người dùng thành công");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("Có lỗi xảy ra! Vui lòng thử lại sau");
		}
		return result;
	}

	public ResponseModel getById(int id) {
		String sql = "SELECT * FROM user WHERE id = ?";
		ResponseModel result = new ResponseModel();
		try {
			Object queryForObject = this.jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserMapper());
			User user = (User)queryForObject;
			result.setSuccess(true);
			result.setMsg("Lấy thông tin người dùng thành công");
			result.setContent(user);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("Lấy thông tin người dùng thất bại");
		}
		return result;
	}

	public ResponseModel search(SearchDTO searchDTO) {
		return null;
	}

}
