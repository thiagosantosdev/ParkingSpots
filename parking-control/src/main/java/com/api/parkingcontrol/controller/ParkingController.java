package com.api.parkingcontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.repository.ParkingSpotRepository;

@RestController
@RequestMapping("/car")

public class ParkingController {

	@Autowired
	private ParkingSpotRepository parkingRepository;
	
	
	

}



	
	
	
	
	