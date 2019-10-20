package com.marx.personmanagement.model.repository;

import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.marx.personmanagement.model.domain.Person;

@PersistenceUnit
@Transactional
@Repository
public class PersonDAO extends GenericDAO<Person, Long> {
	public PersonDAO() {
		super.persistedClass = Person.class;
	}
}
