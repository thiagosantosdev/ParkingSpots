package com.api.parkingcontrol.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.model.ParkingSpots;
import com.api.parkingcontrol.services.ParkingSpotService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")

public class ParkingController {

	@Autowired
	private ParkingSpotService parkingServiceRepository;
	

	@PostMapping("/")
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

	@GetMapping("/")
	public ResponseEntity<Page<ParkingSpots>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(parkingServiceRepository.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
		Optional<ParkingSpots> parkingSpotModelOptional = parkingServiceRepository.findById(id);
		if(!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
			
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
		Optional<ParkingSpots> parkingSpotModelOptional = parkingServiceRepository.findById(id);
		if(!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
		}
		parkingServiceRepository.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully!");
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateParkingspot(@PathVariable(value = "id") UUID id,
			                                        @RequestBody @Valid ParkingSpotDto parkingSpotDto){
		Optional<ParkingSpots> parkingSpotModelOptional = parkingServiceRepository.findById(id);
		if(!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
		}
		 ParkingSpots parkingSpotModel = parkingSpotModelOptional.get();
		 parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
		 parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
		 parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
		 parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
		 parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
		 parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
		 parkingSpotModel.setApartment(parkingSpotDto.getApartment());
		 parkingSpotModel.setBlock(parkingSpotDto.getBlock());
		 
		 return ResponseEntity.status(HttpStatus.OK).body(parkingServiceRepository.save(parkingSpotModel));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}



	
	
	
	
	