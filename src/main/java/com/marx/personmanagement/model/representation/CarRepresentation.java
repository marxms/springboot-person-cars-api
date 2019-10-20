package com.marx.personmanagement.model.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRepresentation {

	@JsonProperty
	private Long id;
	
	@JsonProperty
	private Integer year;
	
	@JsonProperty
	private String licensePlate;
	
	@JsonProperty
	private String model;
	
	@JsonProperty
	private String color;
}
