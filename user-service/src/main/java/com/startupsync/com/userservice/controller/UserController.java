package com.startupsync.com.userservice.controller;



import com.startupsync.com.userservice.payload.UserRequestDto;
import com.startupsync.com.userservice.payload.UserResponseDto;
import com.startupsync.com.userservice.entity.User;
import com.startupsync.com.userservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
		System.out.println(userRequestDto.toString());
		UserResponseDto resp = userService.createUser(userRequestDto);
		return new ResponseEntity<UserResponseDto>(resp, HttpStatus.CREATED);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
		UserResponseDto resp = userService.findUserById(userId);
		return new ResponseEntity<UserResponseDto>(resp, HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserResponseDto> findByEmail(@PathVariable String email) {
		UserResponseDto resp = userService.findByEmail(email);
		return new ResponseEntity<UserResponseDto>(resp, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		List<UserResponseDto> resp =  userService.findAllUsers();
		return new ResponseEntity<List<UserResponseDto>>(resp, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		User resp = userService.updateUser(user);
		return new ResponseEntity<User>(resp, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<Long>(userId, HttpStatus.NO_CONTENT);
	}

}

