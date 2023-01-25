package com.ie.dronesmanagement.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ie.dronesmanagement.entity.DroneEntity;
import com.ie.dronesmanagement.entity.DroneHistoryEntity;
import com.ie.dronesmanagement.repository.DroneHistoryRepository;
import com.ie.dronesmanagement.repository.DroneRepository;

@Component
public class DroneBatteryLevelScheduler {

	@Autowired
	private DroneHistoryRepository droneHistoryRepository;

	@Autowired
	private DroneRepository droneRepository;

	@Scheduled(fixedDelayString = "${app.config.integration.drones-management.config.scheduler-fixed-delay}", initialDelayString = "${app.config.integration.drones-management.config.scheduler-initial-delay}")
	public void logDroneBatteryLevel() {
		List<DroneEntity> droneEntityList = droneRepository.findAll();
		droneEntityList.stream().forEach(drone -> {
			DroneHistoryEntity droneHistoryEntity = new DroneHistoryEntity();
			droneHistoryEntity.setDroneId(drone.getId());
			droneHistoryEntity.setDroneSerialNumber(drone.getSerialNumber());
			droneHistoryEntity.setBatteryCapacity(drone.getBatteryCapacity());
			droneHistoryEntity.setState(drone.getState());
			droneHistoryRepository.save(droneHistoryEntity);
		});
	}
}
