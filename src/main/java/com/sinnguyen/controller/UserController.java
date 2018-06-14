package com.sinnguyen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinnguyen.entities.User;
import com.sinnguyen.model.ResponseModel;
import com.sinnguyen.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
    public ResponseModel deleteUser(@RequestBody User user) {
        return userService.delete(user);
    }
	
	@RequestMapping(value="/edit", method = RequestMethod.PUT)
	public ResponseModel editUser(@RequestBody User user) {
		return userService.edit(user);
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public ResponseModel addUser(@RequestBody User user) {
		return userService.add(user);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseModel getUser(@PathVariable("id") int id) {
		return userService.getById(id);
	}
}
