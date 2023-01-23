package com.ie.dronesmanagement.service;

import java.util.List;

import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.model.BatteryCapacityResponse;
import com.ie.dronesmanagement.model.CreateDroneRequestDto;
import com.ie.dronesmanagement.model.DroneResponseDto;
import com.ie.dronesmanagement.model.LoadDroneRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;

public interface DroneService {

	List<DroneResponseDto> getAllDrones();

	DroneResponseDto getDroneBySerialNumber(String serialNumber) throws BackendException;

	DroneResponseDto registerDrone(CreateDroneRequestDto createDroneRequestDto) throws BackendException;

	DroneResponseDto updateDrone(String serialNumber, UpdateDroneRequestDto updateDroneRequestDto)
			throws BackendException;

	void deleteDrone(String serialNumber) throws BackendException;

	List<DroneResponseDto> getAvailableDronesForLoading();

	DroneResponseDto loadDrone(String serialNumber, LoadDroneRequestDto loadDroneRequestDto);
	
	BatteryCapacityResponse getDroneBatteryCapacity(String serialNumber);
	
	List<MedicationResponseDto> getLoadedMedications(String serialNumber);
}
