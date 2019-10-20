package com.marx.personmanagement.model.representation;

import java.util.List;

import com.marx.personmanagement.model.domain.Role;

import io.swagger.annotations.ApiModelProperty;

public class UserDataDTO {

	@ApiModelProperty(position = 0)
	private String username;
	@ApiModelProperty(position = 1)
	private String password;
	@ApiModelProperty(position = 2)
	private List<Role> roles;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
