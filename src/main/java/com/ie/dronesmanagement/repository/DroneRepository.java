package com.ie.dronesmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ie.dronesmanagement.entity.DroneEntity;
import com.ie.dronesmanagement.enums.DroneStateEnum;

public interface DroneRepository extends JpaRepository<DroneEntity, Long> {

	Optional<DroneEntity> findBySerialNumber(String serialNumber);

	Long deleteBySerialNumber(String serialNumber);

	List<DroneEntity> findByState(DroneStateEnum state);
}
