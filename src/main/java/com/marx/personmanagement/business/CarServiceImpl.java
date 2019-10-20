package com.marx.personmanagement.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.marx.personmanagement.commons.exception.CustomException;
import com.marx.personmanagement.model.domain.Car;
import com.marx.personmanagement.model.domain.Person;
import com.marx.personmanagement.model.repository.CarDAO;
import com.marx.personmanagement.model.representation.CarRepresentation;
import com.marx.personmanagement.model.util.FiltroPesquisa;

import javassist.NotFoundException;

@Service
public class CarServiceImpl implements CarService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CarDAO carDAO;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<CarRepresentation> findCarsByPerson(HttpServletRequest req) {
		
		Person person = getPersonByRequest(req);
		List<CarRepresentation> listResponse = new ArrayList<CarRepresentation>(0);
		
		FiltroPesquisa<Car> filter = new FiltroPesquisa<Car>() {
			
			@Override
			public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<Car> root) {
				
				return criteriaBuilder.equal(root.get("person_id"), person.getId());
			}
		};
		
		for (Car car : carDAO.findByField(filter)) {
			listResponse.add(modelMapper.map(car, CarRepresentation.class));
		}
		return listResponse;
	}

	private Person getPersonByRequest(HttpServletRequest req) {
		Person person = modelMapper.map(userService.whoami(req), Person.class);
		return person;
	}

	@Override
	public CarRepresentation insertCarPerson(Car car, HttpServletRequest req) {
		Person p = getPersonByRequest(req);
		car.setPerson_id(p.getId());
		carDAO.save(car);
		return modelMapper.map(car, CarRepresentation.class);
	}

	@Override
	public CarRepresentation findCarByPerson(Long idCar, HttpServletRequest req) {
		Person person = getPersonByRequest(req);
		FiltroPesquisa<Car> filter = new FiltroPesquisa<Car>() {
			
			@Override
			public Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<Car> root) {
				Predicate predA = criteriaBuilder.equal(root.get("id"), idCar);
				Predicate predB = criteriaBuilder.equal(root.get("person_id"), person.getId());
				return criteriaBuilder.and(predA, predB);
			}
		};
		List<Car> cars = carDAO.findByField(filter);
		if(!cars.isEmpty()) {
			return modelMapper.map(cars.get(0) , CarRepresentation.class);
		} else {
			throw new CustomException("Car Not Found", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void removeCar(Long idCar, HttpServletRequest req) {
		Person person = getPersonByRequest(req);
		Car car = carDAO.findById(idCar);
		if(person.getId().equals(car.getPerson_id())) {
			try {
				carDAO.remove(idCar);
			} catch (NotFoundException e) {
				throw new CustomException("Car Not found.", HttpStatus.NOT_FOUND);
			}
		} else {
			throw new CustomException("Unauthorized to exclude this car.", HttpStatus.UNAUTHORIZED);
		}	
	}

	@Override
	public CarRepresentation updateCar(Car car, HttpServletRequest req) {
		CarRepresentation carRep = findCarByPerson(car.getId(), req);
		if(carRep != null) {
			throw new CustomException("Unauthorized to exclude this car.", HttpStatus.UNAUTHORIZED);
		}
		return modelMapper.map(carDAO.update(car), CarRepresentation.class);
	}

}
