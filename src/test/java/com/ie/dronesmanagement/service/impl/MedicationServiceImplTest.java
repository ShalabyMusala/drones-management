package com.ie.dronesmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.mapper.MedicationMapper;
import com.ie.dronesmanagement.model.CreateMedicationRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;
import com.ie.dronesmanagement.repository.MedicationRepository;
import com.ie.dronesmanagement.util.Constants;

@SpringBootTest
@AutoConfigureMockMvc
class MedicationServiceImplTest {

	@MockBean
	MedicationRepository medicationRepository;

	@MockBean
	MedicationMapper medicationMapper;

	@Autowired
	MedicationServiceImpl medicationServiceImpl;

	@Test
	void test_getAllMedications_success() {
		when(medicationRepository.findAll()).thenReturn(buildMedicationEntityList());
		when(medicationMapper.toMedicationResponseList(any())).thenReturn(buildMedicationsList());
		List<MedicationResponseDto> medicationList = medicationServiceImpl.getAllMedications();
		assertNotNull(medicationList);
		assertEquals(1, medicationList.size());
		assertEquals("MED1", medicationList.get(0).getCode());
	}

	@Test
	void test_getMedicationByCode_success() {
		when(medicationRepository.findByCode(any())).thenReturn(Optional.of(buildMedicationEntity()));
		when(medicationMapper.toMedicationResponseDto(any())).thenReturn(buildMedicationResponseDto());
		MedicationResponseDto medicationResponseDto = medicationServiceImpl.getMedicationByCode("MED1");
		assertNotNull(medicationResponseDto);
		assertEquals("MED1", medicationResponseDto.getCode());
	}

	@Test
	void test_getMedicationByCode_error_codeNotFound() {
		when(medicationRepository.findByCode(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class,
				() -> medicationServiceImpl.getMedicationByCode("A123456789"));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_registerMedication_success() {
		CreateMedicationRequestDto createMedicationRequestDto = new CreateMedicationRequestDto("medication1", 50,
				"MED1", null);
		when(medicationMapper.fromCreateMedicationRequestDto(any())).thenReturn(buildMedicationEntity());
		when(medicationMapper.toMedicationResponseDto(any())).thenReturn(buildMedicationResponseDto());
		MedicationResponseDto medicationResponseDto = medicationServiceImpl
				.registerMedication(createMedicationRequestDto);
		assertNotNull(medicationResponseDto);
		assertEquals("MED1", medicationResponseDto.getCode());
	}

	@Test
	void test_registerMedication_error_codeNotFound() {
		CreateMedicationRequestDto createMedicationRequestDto = new CreateMedicationRequestDto("medication1", 50,
				"MED1", null);
		DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException(
				"duplicate");
		when(medicationRepository.save(any())).thenThrow(dataIntegrityViolationException);
		BackendException backendException = assertThrows(BackendException.class,
				() -> medicationServiceImpl.registerMedication(createMedicationRequestDto));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_updateMedication_success() {
		UpdateMedicationRequestDto updateMedicationRequestDto = new UpdateMedicationRequestDto("medication1", 50,
				"MED1", null);
		when(medicationRepository.findByCode(any())).thenReturn(Optional.of(buildMedicationEntity()));
		when(medicationMapper.toMedicationResponseDto(any())).thenReturn(buildMedicationResponseDto());
		MedicationResponseDto medicationResponseDto = medicationServiceImpl.updateMedication("CODE1",
				updateMedicationRequestDto);
		assertNotNull(medicationResponseDto);
		assertEquals("MED1", medicationResponseDto.getCode());
	}

	@Test
	void test_updateMedication_error_codeNotFound() {
		UpdateMedicationRequestDto updateMedicationRequestDto = new UpdateMedicationRequestDto("medication1", 50,
				"MED1", null);
		when(medicationRepository.findByCode(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class,
				() -> medicationServiceImpl.updateMedication("CODE1", updateMedicationRequestDto));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_updateMedication_error_codeAlreadyExists() {
		UpdateMedicationRequestDto updateMedicationRequestDto = new UpdateMedicationRequestDto("medication1", 50,
				"MED1", null);
		DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException(
				"duplicate");
		when(medicationRepository.save(any())).thenThrow(dataIntegrityViolationException);
		when(medicationRepository.findByCode(any())).thenReturn(Optional.of(buildMedicationEntity()));
		BackendException backendException = assertThrows(BackendException.class,
				() -> medicationServiceImpl.updateMedication("CODE1", updateMedicationRequestDto));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_CODE_ALREADY_EXISTS_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_deleteMedication_success() {
		when(medicationRepository.deleteByCode(any())).thenReturn(Long.valueOf(1));
		assertDoesNotThrow(() -> medicationServiceImpl.deleteMedication("MED1234567"));

	}

	@Test
	void test_deleteMedication_error_codeNotFound() {
		when(medicationRepository.deleteByCode(any())).thenReturn(Long.valueOf(0));
		BackendException backendException = assertThrows(BackendException.class,
				() -> medicationServiceImpl.deleteMedication("MED1234567"));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getReason());
	}

	private MedicationEntity buildMedicationEntity() {
		MedicationEntity medicationEntity = new MedicationEntity();
		medicationEntity.setCode("A123456789");
		medicationEntity.setImageAsBase64("image");
		medicationEntity.setName("Fucidin");
		medicationEntity.setWeight(100);
		return medicationEntity;
	}

	private List<MedicationEntity> buildMedicationEntityList() {
		List<MedicationEntity> medicationEntities = new ArrayList<>();
		medicationEntities.add(buildMedicationEntity());
		return medicationEntities;
	}

	private MedicationResponseDto buildMedicationResponseDto() {
		return new MedicationResponseDto("medication1", 50, "MED1", null);
	}

	private List<MedicationResponseDto> buildMedicationsList() {
		List<MedicationResponseDto> medicationResponseDtos = new ArrayList<>();
		MedicationResponseDto medicationResponseDto = new MedicationResponseDto("medication1", 50, "MED1", null);
		medicationResponseDtos.add(medicationResponseDto);
		return medicationResponseDtos;
	}
}
