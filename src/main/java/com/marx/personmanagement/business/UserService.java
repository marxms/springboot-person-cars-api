package com.marx.personmanagement.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.marx.personmanagement.commons.exception.CustomException;
import com.marx.personmanagement.model.domain.Person;
import com.marx.personmanagement.model.domain.Role;
import com.marx.personmanagement.model.domain.User;
import com.marx.personmanagement.model.repository.UserRepository;
import com.marx.personmanagement.model.representation.PersonRepresentation;
import com.marx.personmanagement.security.JwtTokenProvider;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PersonService personService;
  
  @Autowired
  private ModelMapper modelMapper;
  
  public String signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      Person persistedPerson = personService.findPersonByUsername(username);
      persistedPerson.getUser().setLastLogin(new Date());
      personService.updatePerson(persistedPerson);
      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signup(PersonRepresentation person) {
    if (!userRepository.existsByUsername(person.getLogin())) {
			User user = new User();
			List<Role> roles = new ArrayList<Role>();
			roles.add(Role.ROLE_CLIENT);
			user.setUsername(person.getLogin());
			user.setPassword(person.getPassword());
			user.setRoles(roles);			
			user.setPassword(passwordEncoder.encode(person.getPassword()));
			user.setCreatedAt(new Date());
			user.setLastLogin(new Date());
			Person persistedPerson = modelMapper.map(person, Person.class);
			persistedPerson.setUser(user);
			personService.includePerson(persistedPerson);
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
		} else {
			throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
  }

  public void delete(Long id) {
    personService.removePerson(id);
  }

  public User search(String username) {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }
  public User updatePessoa(User user) {
	    user = userRepository.save(user);
	    if (user == null) {
	      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
	    }
	    return user;
	  }

  public PersonRepresentation whoami(HttpServletRequest req) {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
		PersonRepresentation person = personService.listPersonByIdUser(user.getId().longValue());
		person.setLogin(user.getUsername());
		person.setCreatedAt(user.getCreatedAt());
		person.setLastLogin(user.getLastLogin());
		return person;
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
  }

}
