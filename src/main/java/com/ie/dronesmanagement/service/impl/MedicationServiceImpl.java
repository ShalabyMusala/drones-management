package com.ie.dronesmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ie.dronesmanagement.mapper.MedicationMapper;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;
import com.ie.dronesmanagement.repository.MedicationRepository;
import com.ie.dronesmanagement.service.MedicationService;

@Service
public class MedicationServiceImpl implements MedicationService {

	private final MedicationRepository medicationRepository;
	private final MedicationMapper medicationMapper;

	@Autowired
	public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper) {
		this.medicationRepository = medicationRepository;
		this.medicationMapper = medicationMapper;
	}

	@Override
	public List<MedicationResponseDto> getAllMedications() {
		return medicationMapper.toMedicationResponseList(medicationRepository.findAll());
	}

	@Override
	public MedicationResponseDto updateMedication(UpdateMedicationRequestDto updateMedicationRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
