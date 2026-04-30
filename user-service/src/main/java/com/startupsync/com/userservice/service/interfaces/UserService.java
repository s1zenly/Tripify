package com.startupsync.com.userservice.service.interfaces;


import com.startupsync.com.userservice.payload.UserRequestDto;
import com.startupsync.com.userservice.payload.UserResponseDto;
import com.startupsync.com.userservice.entity.User;

import java.util.List;

public interface UserService {
	UserResponseDto createUser(UserRequestDto userRequestDto);
	UserResponseDto findUserById(Long userId);
	UserResponseDto findByEmail(String email);
	List<UserResponseDto> findAllUsers();
	User updateUser(User user);
	void deleteUser(Long userId);
}

