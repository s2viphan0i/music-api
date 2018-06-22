package com.sinnguyen.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinnguyen.dao.AuthDao;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.UserDTO;
import com.sinnguyen.service.AuthService;

import redis.clients.jedis.Jedis;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthDao authDao;

	public ResponseModel login(UserDTO user) {
		User u = new User();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u = authDao.login(u);
		ResponseModel result = new ResponseModel();
		try {
			if (u != null) {
				user.setId(u.getId());
				user.setFullname(u.getFullname());
				user.setBirthdate(u.getBirthdate());
				user.setEmail(u.getEmail());
				user.setPhone(u.getPhone());
				user.setActivated(u.isActivated());
				user.setNote(u.getNote());
				if (user.isActivated()) {
					Date date = new Date();
					long currentTimeMillis = System.currentTimeMillis();
					Date expireDate = new Date(currentTimeMillis + (24 * 60 * 60 * 10 * 1000));
					Algorithm algorithm = Algorithm.HMAC256("biPHxAMbw7H0mUfV3xO1TIpv0nAQfK41");

					String token = JWT.create().withClaim("id", u.getId()).withClaim("username", user.getUsername())
							.withIssuedAt(date).withExpiresAt(expireDate).sign(algorithm);

					Jedis jedis = new Jedis("localhost");
					jedis.get(token);

					ObjectMapper mapper = new ObjectMapper();
					String userJson = mapper.writeValueAsString(user);

					jedis.set(token, userJson);
					jedis.close();

					user.setToken(token);
					result.setSuccess(true);
					result.setMsg("Đăng nhập thành công!");
					result.setContent(user);
				} else {
					result.setSuccess(false);
					result.setMsg("Vui lòng kích hoạt trước khi đăng nhập");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("Sai tên đăng nhập hoặc mật khẩu");
			}
		} catch (Exception ex) {

		}
		return result;
	}

	public ResponseModel logout(String token) {
		ResponseModel result = new ResponseModel();
		String tokenKey = token.replaceFirst("Bearer ", "");
		Jedis jedis = new Jedis("localhost");
		jedis.del(tokenKey);
		jedis.close();
		result.setSuccess(true);
		result.setMsg("Đăng xuất thành công");
		return result;
	}

	public ResponseModel register(UserDTO user) {

		ResponseModel result = new ResponseModel();
		if (user.getUsername() == null || user.getUsername().equals("") || user.getPassword() == null
				|| user.getPassword().equals("") || user.getFullname() == null || user.getFullname().equals("")
				|| user.getEmail() == null || user.getEmail().equals("")) {
			result.setSuccess(false);
			result.setMsg("Thông tin người dùng không hợp lệ");
		} else {
			User u = new User();
			u.setId(user.getId());
			u.setUsername(user.getUsername());
			u.setPassword(user.getPassword());
			u.setFullname(user.getFullname());
			u.setBirthdate(user.getBirthdate());
			u.setEmail(user.getEmail());
			u.setPhone(user.getPhone());
			u.setActivated(user.isActivated());
			u.setNote(user.getNote());
			if (authDao.checkUsername(u)) {
				if (authDao.register(u)&&authDao.insertActivation(u)) {
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
		if(authDao.activate(code)) {
			result.setSuccess(true);
			result.setMsg("Kích hoạt tài khoản thành công! Vui lòng đăng nhập");
		}else {
			result.setSuccess(false);
			result.setMsg("Có lỗi xảy ra vui lòng thử lại");
		}
		return result;
	}

}
