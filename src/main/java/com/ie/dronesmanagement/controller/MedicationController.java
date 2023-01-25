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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.model.APIResponse;
import com.ie.dronesmanagement.model.APIResponseBuilder;
import com.ie.dronesmanagement.model.CreateMedicationRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;
import com.ie.dronesmanagement.service.MedicationService;
import com.ie.dronesmanagement.util.Constants;

@RestController
@RequestMapping("${app.config.integration.drones-management.base-uri}")
public class MedicationController {

	@Autowired
	private MedicationService medicationService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.medication}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<List<MedicationResponseDto>> getAllMedications() {
		List<MedicationResponseDto> medicationsList = medicationService.getAllMedications();
		return new APIResponseBuilder<List<MedicationResponseDto>>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(medicationsList).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "${app.config.integration.drones-management.api.medication-using-path-param-code}", produces = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<MedicationResponseDto> getMedicationByMedicationCode(@PathVariable("code") String code)
			throws BackendException {
		MedicationResponseDto medicationResponseDto = medicationService.getMedicationByCode(code);
		return new APIResponseBuilder<MedicationResponseDto>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(medicationResponseDto)
				.build();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping(value = "${app.config.integration.drones-management.api.medication}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<MedicationResponseDto> registerMedication(
			@Valid @RequestBody CreateMedicationRequestDto createMedicationRequestDto) throws BackendException {
		MedicationResponseDto medicationResponseDto = medicationService.registerMedication(createMedicationRequestDto);
		return new APIResponseBuilder<MedicationResponseDto>().code(2001).addMessage("Created").reason("Created")
				.body(medicationResponseDto).build();
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = "${app.config.integration.drones-management.api.medication-using-path-param-code}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public APIResponse<MedicationResponseDto> updateMedication(@PathVariable("code") String code,
			@Valid @RequestBody UpdateMedicationRequestDto updateMedicationRequestDto) throws BackendException {
		MedicationResponseDto medicationResponseDto = medicationService.updateMedication(code,
				updateMedicationRequestDto);
		return new APIResponseBuilder<MedicationResponseDto>().code(Constants.SUCCESS_CODE)
				.addMessage(Constants.SUCCESS_MESSAGE).reason(Constants.SUCCESS_MESSAGE).body(medicationResponseDto)
				.build();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "${app.config.integration.drones-management.api.medication-using-path-param-code}")
	public APIResponse<Object> deleteMedication(@PathVariable("code") String code) throws BackendException {
		medicationService.deleteMedication(code);
		return new APIResponseBuilder<Object>().code(2004).addMessage(Constants.SUCCESS_MESSAGE)
				.reason(Constants.SUCCESS_MESSAGE).build();
	}

}
