package com.marx.personmanagement.model.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseRepresentation {

	@JsonProperty
	private String token;
	
	@JsonProperty
	private Integer status;
	
}
