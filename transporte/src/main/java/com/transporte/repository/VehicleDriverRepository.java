package com.transporte.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.transporte.model.VehicleDriver;

@Repository
public interface VehicleDriverRepository extends JpaRepository<VehicleDriver, Long>{

	@Query("SELECT va FROM VehicleDriver va WHERE va.vehicle.id = :vehicleId AND "
			+ "va.disaffiliationDate IS NULL")
	List<VehicleDriver> findByVehicleId(Long vehicleId);
	
	@Query("SELECT va FROM VehicleDriver va WHERE va.vehicle.id = :vehicleId AND va.driver.id = :driverId ")
	Optional<VehicleDriver> findbyVehicleAndDriver(Long vehicleId, Long driverId);
	
	@Modifying
	@Query("UPDATE VehicleDriver u SET u.disaffiliationDate = :disaffiliationDate WHERE u.vehicle.id = :vehicleId AND u.driver.id = :driverId")
	void updateDisaffiliationDate(LocalDateTime disaffiliationDate, Long vehicleId, Long driverId);
	
	@Query("SELECT va FROM VehicleDriver va WHERE va.vehicle.id = :vehicleId AND va.driver.id = :driverId AND "
			+ "va.disaffiliationDate IS NULL")
	Optional<VehicleDriver> findLinkByVehicleAndDriver(Long vehicleId, Long driverId);
}
