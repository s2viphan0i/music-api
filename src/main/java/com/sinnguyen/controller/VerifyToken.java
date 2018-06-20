package com.sinnguyen.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;

import redis.clients.jedis.Jedis;

public class VerifyToken {
	public User user = new User();
	
	public VerifyToken() {
		
	}
	
	public boolean checkToken(String token){
		if(token != null && !token.isEmpty()){
			String tokenKey = token.replaceFirst("Bearer ", "");
			Jedis jedis = new Jedis("localhost");
			String userInfo = jedis.get(tokenKey);
			if(userInfo != null && !userInfo.isEmpty()){
				ObjectMapper mapper = new ObjectMapper();
				try {
					user = mapper.readValue(userInfo, User.class);
					jedis.close();
					return true;
				} catch (Exception e) {
					user = null;
				}
			}
			jedis.close();
		}
		return false;
	}
	public ResponseModel notFoundUser(){
		ResponseModel result = new ResponseModel();
		result.setSuccess(false);
		result.setMsg("Not found User");
		return result;
	}
	public ResponseModel notFoundObject(){
		ResponseModel result = new ResponseModel();
		result.setSuccess(false);
		result.setMsg("Bản ghi không tồn tại");
		return result;
	}
}
