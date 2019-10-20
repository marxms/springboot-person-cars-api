package com.marx.personmanagement.model.representation;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.marx.personmanagement.model.domain.Car;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRepresentation {

	@JsonProperty
	private Long id;
	
	@JsonProperty
	private String firstName;
	
	@JsonProperty
	private String lastName;
	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private Date birthday;
	
	@JsonProperty
	private String login;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String phone;
	
	@JsonProperty
	private List<Car> cars;

	@JsonProperty
	private Date createdAt;
	
	@JsonProperty
	private Date lastLogin;
}
