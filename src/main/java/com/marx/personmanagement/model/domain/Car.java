package com.marx.personmanagement.model.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_car")
@Getter
@Setter
public class Car implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8907542208591828803L;

	@Id
	@GenericGenerator(name="carIncrement" , strategy="increment")
	@GeneratedValue(generator="carIncrement")
	private Long id;
	
	@Column
	private Integer year;
	
	@Column
	private String licensePlate;
	
	@Column
	private String model;
	
	@Column
	private String color;

	@Column
	private Long person_id;
	
	
}
