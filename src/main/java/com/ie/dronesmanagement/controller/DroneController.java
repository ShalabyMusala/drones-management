package com.ie.dronesmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.model.APIResponse;
import com.ie.dronesmanagement.model.APIResponseBuilder;
import com.ie.dronesmanagement.model.BatteryCapacityResponse;
import com.ie.dronesmanagement.model.CreateDroneRequestDto;
import com.ie.dronesmanagement.model.DroneResponseDto;
import com.ie.dronesmanagement.model.LoadDroneRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;
import com.ie.dronesmanagement.service.DroneService;
import com.ie.dronesmanagement.util.Constants;

@RestController
@RequestMapping("${app.config.integration.drones-management.base-uri}")
public class DroneController {

	@Autowired
	private DroneService droneService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.drone}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<List<DroneResponseDto>> getAllDrones() {
		List<DroneResponseDto> dronesList = droneService.getAllDrones();
		return new APIResponseBuilder<List<DroneResponseDto>>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(dronesList).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.drone-using-path-param-drone-serial-number}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<DroneResponseDto> getDroneBySerialNumber(@PathVariable("serialNumber") String serialNumber)
			throws BackendException {
		DroneResponseDto droneResponseDto = droneService.getDroneBySerialNumber(serialNumber);
		return new APIResponseBuilder<DroneResponseDto>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(droneResponseDto).build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping(value = "${app.config.integration.drones-management.api.drone}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<DroneResponseDto> registerDrone(@Valid @RequestBody CreateDroneRequestDto createDroneRequestDto)
			throws BackendException {
		DroneResponseDto droneResponseDto = droneService.registerDrone(createDroneRequestDto);
		return new APIResponseBuilder<DroneResponseDto>().code(2001).addMessage("Created").reason("Created")
				.body(droneResponseDto).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = "${app.config.integration.drones-management.api.drone-using-path-param-drone-serial-number}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<DroneResponseDto> updateDrone(@PathVariable("serialNumber") String serialNumber,
			@Valid @RequestBody UpdateDroneRequestDto updateDroneRequestDto) throws BackendException {
		DroneResponseDto droneResponseDto = droneService.updateDrone(serialNumber, updateDroneRequestDto);
		return new APIResponseBuilder<DroneResponseDto>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(droneResponseDto).build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "${app.config.integration.drones-management.api.drone-using-path-param-drone-serial-number}")
	public APIResponse<Object> deleteDrone(@PathVariable("serialNumber") String serialNumber) throws BackendException {
		droneService.deleteDrone(serialNumber);
		return new APIResponseBuilder<Object>().code(2004).addMessage(Constants.SUCCESS_MESSAGE)
				.reason(Constants.SUCCESS_MESSAGE).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.get-available-drones}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<List<DroneResponseDto>> getAvailableDrones() {
		List<DroneResponseDto> dronesList = droneService.getAvailableDronesForLoading();
		return new APIResponseBuilder<List<DroneResponseDto>>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(dronesList).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "${app.config.integration.drones-management.api.load-drone-with-medication-items}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<DroneResponseDto> loadDrone(@PathVariable("serialNumber") String serialNumber,
			@Valid @RequestBody LoadDroneRequestDto loadDroneRequestDto) throws BackendException {
		DroneResponseDto droneResponseDto = droneService.loadDrone(serialNumber, loadDroneRequestDto);
		return new APIResponseBuilder<DroneResponseDto>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(droneResponseDto).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.check-drone-battery-capacity}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<BatteryCapacityResponse> getDroneBatterCapacity(
			@PathVariable("serialNumber") String serialNumber) {
		BatteryCapacityResponse batteryCapacity = droneService.getDroneBatteryCapacity(serialNumber);
		return new APIResponseBuilder<BatteryCapacityResponse>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(batteryCapacity).build();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.get-drone-loaded-medications}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<List<MedicationResponseDto>> getDroneLoadedMedications(
			@PathVariable("serialNumber") String serialNumber) {
		List<MedicationResponseDto> loadedMedications = droneService.getLoadedMedications(serialNumber);
		return new APIResponseBuilder<List<MedicationResponseDto>>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(loadedMedications).build();
	}
	
}
