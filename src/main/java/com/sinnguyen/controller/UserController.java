package com.sinnguyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.model.UserDTO;
import com.sinnguyen.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController{
	
	public UserController() {
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
    public ResponseModel deleteUser(@RequestBody User user) {
        return userService.delete(user);
    }
	
	@RequestMapping(value="/edit", method = RequestMethod.PUT)
	public ResponseModel editUser(@RequestBody UserDTO user) {
		SecurityContext context = SecurityContextHolder.getContext();
		String username = context.getAuthentication().getName();
		user.setUsername(username);
		return userService.edit(user);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseModel getUser(@PathVariable("id") int id) {
		return userService.getById(id);
	}
}
