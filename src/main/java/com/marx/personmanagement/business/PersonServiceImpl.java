package com.marx.personmanagement.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marx.personmanagement.commons.exception.CustomException;
import com.marx.personmanagement.model.domain.Person;
import com.marx.personmanagement.model.repository.PersonDAO;
import com.marx.personmanagement.model.representation.PersonRepresentation;
import com.marx.personmanagement.model.util.FiltroPesquisa;

import javassist.NotFoundException;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

	@Autowired
	PersonDAO personDAO;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<PersonRepresentation> listAllPersons() {
		
		List<PersonRepresentation> listResponse = new ArrayList<PersonRepresentation>(0);
		for(Person person : personDAO.getList()) {
			listResponse.add(modelMapper.map(person, PersonRepresentation.class));
		}
		return listResponse;
	}

	@Override
	public PersonRepresentation includePerson(Person person) {
		return modelMapper.map(personDAO.save(person), PersonRepresentation.class);
	}

	@Override
	public void removePerson(Long id) {
		try {
			personDAO.remove(id);
		} catch (NotFoundException e) {
			throw new CustomException("Not found", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public PersonRepresentation updatePerson(Person person) {
		return modelMapper.map(personDAO.update(person), PersonRepresentation.class);
	}
	
	@Override
	public PersonRepresentation listPersonByID(Long id) {
		Person person = personDAO.findById(id);
		if(person != null) {
			PersonRepresentation response = modelMapper.map(person, PersonRepresentation.class);
			response.setLogin(person.getUser().getUsername());
			return response;
		} else {
			throw new CustomException("Person not found", HttpStatus.NOT_FOUND);
		}
	}
	
	
	@Override
	public PersonRepresentation listPersonByIdUser(Long idUser) {
		FiltroPesquisa<Person> filter = new FiltroPesquisa<Person>() {
			
			@Override
			public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<Person> root) {
				
				return criteriaBuilder.equal(root.get("user").get("id"), idUser);
			}
		};
		return modelMapper.map(personDAO.findByField(filter).get(0), PersonRepresentation.class);
	}

	@Override
	public Person findPersonByUsername(String login) {
		FiltroPesquisa<Person> filter = new FiltroPesquisa<Person>() {
			
			@Override
			public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<Person> root) {
				
				return criteriaBuilder.equal(root.get("user").get("username"), login);
			}
		};
		return personDAO.findByField(filter).get(0);
	}
	
}
