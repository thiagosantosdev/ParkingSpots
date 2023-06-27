package com.api.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api.parkingcontrol.model.ParkingSpots;
import com.api.parkingcontrol.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {
	
	@Autowired
	ParkingSpotRepository parkingSpotRepository;

	@Transactional
	public ParkingSpots save(ParkingSpots parkingSpotModel) {

		return parkingSpotRepository.save(parkingSpotModel);
	}

	public boolean existsByLicensePlateCar(String licensePlateCar) {

		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
		
	}

	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
		
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}

	public Page<ParkingSpots> findAll(Pageable pageable) {
		
		return parkingSpotRepository.findAll(pageable);
	}

	public Optional<ParkingSpots> findById(UUID id) {

		return parkingSpotRepository.findById(id);
	}

	@Transactional
	public void delete(ParkingSpots parkingSpots) {

		parkingSpotRepository.delete(parkingSpots);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
