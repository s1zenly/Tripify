package com.startupsync.com.userservice.service;



import com.startupsync.com.userservice.entity.User;
import com.startupsync.com.userservice.payload.UserRequestDto;
import com.startupsync.com.userservice.payload.UserResponseDto;
import com.startupsync.com.userservice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements com.startupsync.com.userservice.service.interfaces.UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		User user = this.modelMapper.map(userRequestDto, User.class);
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		this.userRepository.save(user);
		return this.modelMapper.map(user, UserResponseDto.class);
	}
	@Override
	public UserResponseDto findUserById(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		return this.modelMapper.map(user, UserResponseDto.class);
	}
	@Override
	public UserResponseDto findByEmail(String userId) {
		User user = userRepository.findByEmail(userId);
		return this.modelMapper.map(user, UserResponseDto.class);
	}
	@Override
	public List<UserResponseDto> findAllUsers() {
		List<UserResponseDto> allUsers = userRepository.findAll().stream().map((user -> this.modelMapper.map(user, UserResponseDto.class))).collect(Collectors.toList());
		return allUsers;
	}
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
		// User user = this.userRepository.findById(userId).orElse(null);
		// User updatedUser = user
		// 		.builder()
		// 		.id(user.getId())
		// 		.name(userRequestDto.getName())
		// 		.userName(userRequestDto.getUserName())
		// 		.password(userRequestDto.getPassword())
		// 		.email(userRequestDto.getEmail())
		// 		.designation(userRequestDto.getDesignation())
		// 		.bio(userRequestDto.getBio())
		// 		.profileImageUrl(userRequestDto.getProfileImageUrl())
		// 		.location(userRequestDto.getLocation())
		// 		.github(userRequestDto.getGithub())
		// 		.linkedin(userRequestDto.getLinkedin())
		// 		.createdAt(user.getCreatedAt())
		// 		.updatedAt(new Date())
		// 		.build();
		// return this.modelMapper.map(updatedUser, UserResponseDto.class);
	}
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}

