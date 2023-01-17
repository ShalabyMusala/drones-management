package com.ie.dronesmanagement.service;

import java.util.List;

import com.ie.dronesmanagement.model.MedicationDto;

public interface MedicationService {
	
	List<MedicationDto> getAllMedications();
	
	MedicationDto updateMedication();
	
}
