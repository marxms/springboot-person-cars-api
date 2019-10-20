package com.marx.personmanagement.business;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.marx.personmanagement.model.domain.Car;
import com.marx.personmanagement.model.representation.CarRepresentation;

public interface CarService {

	public List<CarRepresentation> findCarsByPerson(HttpServletRequest req);
	
	public CarRepresentation insertCarPerson(Car car, HttpServletRequest req);
	
	public CarRepresentation findCarByPerson(Long idCar, HttpServletRequest req);
	
	public void removeCar(Long idCar, HttpServletRequest req);
	
	public CarRepresentation updateCar(Car car, HttpServletRequest req);

}
