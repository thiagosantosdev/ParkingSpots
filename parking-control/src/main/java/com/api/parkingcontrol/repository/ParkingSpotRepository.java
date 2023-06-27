package com.api.parkingcontrol.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.api.parkingcontrol.model.ParkingSpots;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpots, UUID> {
  
	boolean existsByLicensePlateCar(String licensePlateCar);
	boolean existsByParkingSpotNumber(String parkingSpotNumber);
	boolean existsByApartmentAndBlock(String apartment, String block);
	
}
