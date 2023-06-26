package com.api.parkingcontrol.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpots;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@RequestMapping("/parking-spot")

public class ParkingController {

	@Autowired
	private ParkingSpotService parkingServiceRepository;
	
	@GetMapping
	public String index() {
		
		return "Olá Mundo!";
	}
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
		
		 if(parkingServiceRepository.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())){
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
	        }
	        if(parkingServiceRepository.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
	        }
	        if(parkingServiceRepository.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
	        }
		
		var parkingSpotModel = new ParkingSpots();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingServiceRepository.save(parkingSpotModel));
	}

}



	
	
	
	
	