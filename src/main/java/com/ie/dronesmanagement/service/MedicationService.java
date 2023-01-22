package com.ie.dronesmanagement.service;

import java.util.List;

import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.model.CreateMedicationRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;

public interface MedicationService {

	List<MedicationResponseDto> getAllMedications();

	MedicationResponseDto getMedicationByCode(String code) throws BackendException;

	MedicationResponseDto registerMedication(CreateMedicationRequestDto createMedicationRequestDto)
			throws BackendException;

	MedicationResponseDto updateMedication(String code, UpdateMedicationRequestDto updateMedicationRequestDto)
			throws BackendException;

	void deleteMedication(String code) throws BackendException;

}
