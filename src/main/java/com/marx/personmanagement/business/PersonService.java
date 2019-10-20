package com.marx.personmanagement.business;

import java.util.List;

import com.marx.personmanagement.model.domain.Person;
import com.marx.personmanagement.model.representation.PersonRepresentation;

public interface PersonService {

	
	public List<PersonRepresentation> listAllPersons();
	
	public PersonRepresentation includePerson(Person person);
	
	public void removePerson(Long id);
	
	public PersonRepresentation updatePerson(Person person);

	public PersonRepresentation listPersonByID(Long id);

	public PersonRepresentation listPersonByIdUser(Long idUser);
	
	public Person findPersonByUsername(String login);
}
