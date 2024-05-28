package com.codewithdurgesh.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import com.codewithdurgesh.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4,message = "user name must be minimum 4 character!!")
	private String name;
	
	@Email(message = "emil address not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 5,message = "password must be minimum 3 character and max 10character !!")
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDto> roles=new HashSet();
	

}
