package com.ie.dronesmanagement.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.mapper.MedicationMapper;
import com.ie.dronesmanagement.model.CreateMedicationRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;
import com.ie.dronesmanagement.repository.MedicationRepository;
import com.ie.dronesmanagement.service.MedicationService;
import com.ie.dronesmanagement.util.Constants;
import com.ie.dronesmanagement.util.ValidationUtil;

@Service
@Transactional
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
	public MedicationResponseDto getMedicationByCode(String code) throws BackendException {
		MedicationEntity medicationEntity = medicationRepository.findByCode(code)
				.orElseThrow(() -> new BackendException(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE,
						Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE,
						Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value()));
		return medicationMapper.toMedicationResponseDto(medicationEntity);
	}

	@Override
	public MedicationResponseDto registerMedication(CreateMedicationRequestDto createMedicationRequestDto)
			throws BackendException {
		try {
			return medicationMapper.toMedicationResponseDto(medicationRepository
					.save(medicationMapper.fromCreateMedicationRequestDto(createMedicationRequestDto)));
		} catch (DuplicateKeyException ex) {
			throw new BackendException(ex.getMessage(), Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_CODE,
					Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	@Override
	public MedicationResponseDto updateMedication(String code, UpdateMedicationRequestDto updateMedicationRequestDto)
			throws BackendException {
		ValidationUtil.validateUpdateMedicationRequestDto(updateMedicationRequestDto);
		MedicationEntity medicationEntity = medicationRepository.findByCode(code)
				.orElseThrow(() -> new BackendException(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE,
						Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE,
						Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value()));
		if (StringUtils.isNotBlank(updateMedicationRequestDto.getCode())) {
			medicationEntity.setCode(updateMedicationRequestDto.getCode());
		}
		if (StringUtils.isNotBlank(updateMedicationRequestDto.getName())) {
			medicationEntity.setName(updateMedicationRequestDto.getName());
		}
		if (StringUtils.isNotBlank(updateMedicationRequestDto.getImageAsBase64())) {
			medicationEntity.setImageAsBase64(updateMedicationRequestDto.getImageAsBase64());
		}
		if (updateMedicationRequestDto.getWeight() != null) {
			medicationEntity.setWeight(updateMedicationRequestDto.getWeight());
		}
		try {
			medicationRepository.save(medicationEntity);
		} catch (DuplicateKeyException ex) {
			throw new BackendException(ex.getMessage(), Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_CODE,
					Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
		return medicationMapper.toMedicationResponseDto(medicationEntity);
	}

	@Override
	public void deleteMedication(String code) throws BackendException {
		Long deleted = medicationRepository.deleteByCode(code);
		if (deleted == 0) {
			throw new BackendException(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE,
					Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE,
					Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}
}
