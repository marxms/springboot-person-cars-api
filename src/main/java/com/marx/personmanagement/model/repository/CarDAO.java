package com.marx.personmanagement.model.repository;

import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.marx.personmanagement.model.domain.Car;

@PersistenceUnit
@Transactional
@Repository
public class CarDAO extends GenericDAO<Car, Long> {

	public CarDAO() {
		super.persistedClass = Car.class;
	}
}
