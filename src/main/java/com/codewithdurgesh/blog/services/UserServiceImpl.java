package com.codewithdurgesh.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.exceptions.ResourceNotFoundException;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.repositories.UserRepo;

	@Service
	public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	
	@Override
	public UserDto createUser(UserDto userDto) 
	{	
		User user=this.dtoTOUser(userDto);
		User saveduser = this.userRepo.save(user);
		return this.UserTODto(saveduser);
	}

	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) 
	{
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " id ", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.UserTODto(updatedUser);

        return userDto1;
	}

	
	@Override
	public UserDto getUserById(Integer userId) 
	{
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id", userId));
		
		return this.UserTODto(user);
	}

	
	@Override
	public List<UserDto> getallUsers() {
		
	    List<User>users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user -> this.UserTODto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", " Id ", userId));
		this.userRepo.delete(user);
		
	}
	
	private User dtoTOUser(UserDto userDto)
	{
		User user = this.modelmapper.map(userDto, User.class);
		return user;
		
		/*
		 * User user = new User(); user.setId(userDto.getId());
		 * user.setName(userDto.getName()); user.setEmail(userDto.getEmail());
		 * user.setPassword(userDto.getPassword()); user.setAbout(userDto.getAbout());
		 * return user;
		 */		
	}
	
	private UserDto UserTODto(User user) 
	{
		UserDto userDto = this.modelmapper.map(user, UserDto.class);
		return userDto;
		/*
		 * UserDto userDto=new UserDto(); userDto.setId(user.getId());
		 * userDto.setName(user.getName()); userDto.setEmail(user.getEmail());
		 * userDto.setPassword(user.getPassword()); userDto.setAbout(user.getAbout());
		 * return userDto;
		 */
		
	}



}
