package com.marx.personmanagement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marx.personmanagement.business.CarService;
import com.marx.personmanagement.model.domain.Car;
import com.marx.personmanagement.model.representation.CarRepresentation;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/cars")
@Api(tags = "cars")
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping
	public List<CarRepresentation> listCarsFromPerson(HttpServletRequest req) {
		return carService.findCarsByPerson(req);
	}
	
	@GetMapping(path = "/{id}")
	public CarRepresentation listCarsFromPerson(HttpServletRequest req, @PathVariable Long id) {
		return carService.findCarByPerson(id, req);
	}
	
	@PostMapping
	public CarRepresentation insertCarByPerson(@RequestBody CarRepresentation car, HttpServletRequest req) {
		return modelMapper.map(carService.insertCarPerson(modelMapper.map(car, Car.class), req), CarRepresentation.class);
	}
	
	@PutMapping(path = "/{id}")
	public CarRepresentation updateCar(@RequestBody CarRepresentation car, HttpServletRequest req) {
		return modelMapper.map(carService.updateCar(modelMapper.map(car, Car.class), req), CarRepresentation.class);
	}
	
	@DeleteMapping(path = "/{id}")
	public CarRepresentation deleteCar(@RequestBody CarRepresentation car, HttpServletRequest req) {
		return modelMapper.map(carService.insertCarPerson(modelMapper.map(car, Car.class), req), CarRepresentation.class);
	}
}
