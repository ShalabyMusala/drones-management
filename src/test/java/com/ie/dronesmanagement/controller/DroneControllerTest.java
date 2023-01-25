package com.ie.dronesmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ie.dronesmanagement.enums.DroneModelEnum;
import com.ie.dronesmanagement.enums.DroneStateEnum;
import com.ie.dronesmanagement.model.BatteryCapacityResponse;
import com.ie.dronesmanagement.model.CreateDroneRequestDto;
import com.ie.dronesmanagement.model.DroneResponseDto;
import com.ie.dronesmanagement.model.LoadDroneRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;
import com.ie.dronesmanagement.service.DroneService;

@WebMvcTest(DroneController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DroneControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	DroneService droneService;

	@InjectMocks
	DroneController droneController;

	@Value("${app.config.integration.drones-management.base-uri}")
	private String baseUri;

	@Value("${app.config.integration.drones-management.api.drone}")
	private String droneUri;

	@Value("${app.config.integration.drones-management.api.drone-using-path-param-drone-serial-number}")
	private String droneSerialNumberUri;

	@Value("${app.config.integration.drones-management.api.get-available-drones}")
	private String availableDronesUri;

	@Value("${app.config.integration.drones-management.api.check-drone-battery-capacity}")
	private String droneBatteryCapacityUri;

	@Value("${app.config.integration.drones-management.api.load-drone-with-medication-items}")
	private String loadDroneUri;

	@Value("${app.config.integration.drones-management.api.get-drone-loaded-medications}")
	private String droneLoadedMedicationsUri;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void test_getAllDrones_expect200() throws Exception {
		when(droneService.getAllDrones()).thenReturn(buildDroneResponseList());
		this.mvc.perform(
				get(baseUri + droneUri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_getDroneBySerialNumber_expect200() throws Exception {
		when(droneService.getDroneBySerialNumber(any())).thenReturn(buildDrone());
		this.mvc.perform(get(baseUri + droneSerialNumberUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_registerDrone_expect201() throws Exception {
		CreateDroneRequestDto createDroneRequestDto = new CreateDroneRequestDto("12345",
				DroneModelEnum.CRUISER_WEIGHT.name(), 400, 100, DroneStateEnum.IDLE.name());
		when(droneService.registerDrone(any())).thenReturn(buildDrone());
		this.mvc.perform(put(baseUri + droneUri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(createDroneRequestDto)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2001));

	}

	@Test
	void test_updateDrone_expect200() throws Exception {
		UpdateDroneRequestDto updateDroneRequestDto = new UpdateDroneRequestDto(null, null, null, 50,
				DroneStateEnum.DELIVERED.name());
		when(droneService.updateDrone(any(), any())).thenReturn(buildDrone());
		this.mvc.perform(patch(baseUri + droneSerialNumberUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(updateDroneRequestDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_deleteDrone_expect204() throws Exception {
		doNothing().when(droneService).deleteDrone(any());
		this.mvc.perform(delete(baseUri + droneSerialNumberUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2004));

	}

	@Test
	void test_getAvailableDrones_expect200() throws Exception {
		when(droneService.getAvailableDronesForLoading()).thenReturn(buildDroneResponseList());
		this.mvc.perform(get(baseUri + availableDronesUri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_getDroneBatterCapacity_expect200() throws Exception {
		when(droneService.getDroneBatteryCapacity(any())).thenReturn(new BatteryCapacityResponse(80));
		this.mvc.perform(get(baseUri + droneBatteryCapacityUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_getDroneLoadedMedications_expect200() throws Exception {
		when(droneService.getLoadedMedications(any())).thenReturn(buildMedicationsList());
		this.mvc.perform(get(baseUri + droneLoadedMedicationsUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_loadDrone_expect200() throws Exception {
		LoadDroneRequestDto loadDroneRequestDto = new LoadDroneRequestDto(Arrays.asList("MED1", "MED2", "MED3"));
		when(droneService.loadDrone(any(), any())).thenReturn(buildDrone());
		this.mvc.perform(post(baseUri + loadDroneUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(loadDroneRequestDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	private List<DroneResponseDto> buildDroneResponseList() {
		List<DroneResponseDto> dronesList = new ArrayList<>();
		DroneResponseDto drone = new DroneResponseDto("12345", DroneModelEnum.CRUISER_WEIGHT, 400, 100,
				DroneStateEnum.IDLE, null);
		dronesList.add(drone);
		return dronesList;
	}

	private DroneResponseDto buildDrone() {
		return new DroneResponseDto("12345", DroneModelEnum.CRUISER_WEIGHT, 400, 100, DroneStateEnum.IDLE, null);
	}

	private List<MedicationResponseDto> buildMedicationsList() {
		List<MedicationResponseDto> medicationResponseDtos = new ArrayList<>();
		MedicationResponseDto medicationResponseDto = new MedicationResponseDto("medication1", 50, "MED1", null);
		medicationResponseDtos.add(medicationResponseDto);
		return medicationResponseDtos;
	}

}
