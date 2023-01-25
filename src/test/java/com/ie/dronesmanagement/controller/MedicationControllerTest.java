package com.ie.dronesmanagement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;
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
import com.ie.dronesmanagement.model.CreateMedicationRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;
import com.ie.dronesmanagement.service.MedicationService;

@WebMvcTest(MedicationController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicationControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	MedicationService medicationService;

	@InjectMocks
	MedicationController medicationController;

	@Value("${app.config.integration.drones-management.base-uri}")
	private String baseUri;

	@Value("${app.config.integration.drones-management.api.medication}")
	private String medicationUri;

	@Value("${app.config.integration.drones-management.api.medication-using-path-param-code}")
	private String medicationCodeUri;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void test_getAllMedications_expect200() throws Exception {
		when(medicationService.getAllMedications()).thenReturn(buildMedicationsList());
		this.mvc.perform(
				get(baseUri + medicationUri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_getMedicationByMedicationCode_expect200() throws Exception {
		when(medicationService.getMedicationByCode(any())).thenReturn(buildMedicationResponseDto());
		this.mvc.perform(get(baseUri + medicationCodeUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_registerMedication_expect201() throws Exception {
		CreateMedicationRequestDto createmedicationRequestDto = new CreateMedicationRequestDto("medication1", 50,
				"MED1234567", null);
		when(medicationService.registerMedication(any())).thenReturn(buildMedicationResponseDto());
		this.mvc.perform(
				put(baseUri + medicationUri).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(createmedicationRequestDto)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2001));

	}

	@Test
	void test_updateMedication_expect200() throws Exception {
		UpdateMedicationRequestDto updateMedicationRequestDto = new UpdateMedicationRequestDto(null, 40, null, null);
		when(medicationService.updateMedication(any(), any())).thenReturn(buildMedicationResponseDto());
		this.mvc.perform(patch(baseUri + medicationCodeUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(updateMedicationRequestDto)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2000));

	}

	@Test
	void test_deleteMedication_expect204() throws Exception {
		doNothing().when(medicationService).deleteMedication(any());
		this.mvc.perform(delete(baseUri + medicationCodeUri, "12345").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(2004));

	}

	private List<MedicationResponseDto> buildMedicationsList() {
		List<MedicationResponseDto> medicationResponseDtos = new ArrayList<>();
		MedicationResponseDto medicationResponseDto = new MedicationResponseDto("medication1", 50, "MED1", null);
		medicationResponseDtos.add(medicationResponseDto);
		return medicationResponseDtos;
	}

	private MedicationResponseDto buildMedicationResponseDto() {
		MedicationResponseDto medicationResponseDto = new MedicationResponseDto("medication1", 50, "MED1", null);
		return medicationResponseDto;
	}
}
