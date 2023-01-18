package com.ie.dronesmanagement.service;

import java.util.List;

import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;

public interface MedicationService {

	List<MedicationResponseDto> getAllMedications();

	MedicationResponseDto updateMedication(UpdateMedicationRequestDto updateMedicationRequestDto);

}
