package com.sinnguyen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinnguyen.dao.UserDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.UserDTO;
import com.sinnguyen.service.UserService;
import com.sinnguyen.util.Converter;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	public ResponseModel add(UserDTO user) {

		ResponseModel result = new ResponseModel();
		if (user.getUsername() == null || user.getUsername().equals("") || user.getPassword() == null
				|| user.getPassword().equals("") || user.getFullname() == null || user.getFullname().equals("")
				|| user.getEmail() == null || user.getEmail().equals("")) {
			result.setSuccess(false);
			result.setMsg("Thông tin người dùng không hợp lệ");
		} else {
			User u = Converter.userDTOToUser(user);
			if (userDao.checkUsername(u)) {
				if (userDao.add(u)&&userDao.insertActivation(u)) {
					user.setId(u.getId());
					user.setCode(u.getCode());
					result.setSuccess(true);
					result.setMsg("Đăng kí thành công");
					result.setContent(user);
				} else {
					result.setSuccess(false);
					result.setMsg("Có lỗi xảy ra! Vui lòng kiểm tra lại");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("Tên đăng nhập hoặc email đã tồn tại");
			}
		}
		return result;
	}
	
	public ResponseModel activate(String code) {
		ResponseModel result = new ResponseModel();
		if(userDao.activate(code)) {
			result.setSuccess(true);
			result.setMsg("Kích hoạt tài khoản thành công! Vui lòng đăng nhập");
		}else {
			result.setSuccess(false);
			result.setMsg("Có lỗi xảy ra vui lòng thử lại");
		}
		return result;
	}

	public ResponseModel edit(User user) {
		return null;
	}

	public ResponseModel delete(User user) {
		return null;

	}

	public ResponseModel getById(int id) {
		return null;
	}
	
	public ResponseModel getByUsername(String username) {
		ResponseModel result = new ResponseModel();
		User u = userDao.getUserbyUsername(username);
		
		if(u!=null) {
			UserDTO user = Converter.userToUserDTO(u);
			result.setSuccess(true);
			result.setMsg("Đăng nhập thành công");
			result.setContent(user);
		} else {
			result.setSuccess(false);
			result.setMsg("Có lỗi xảy ra! Vui lòng thử lại");
		}
		return result;
	}
}
