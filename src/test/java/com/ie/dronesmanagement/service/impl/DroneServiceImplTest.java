package com.ie.dronesmanagement.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import com.ie.dronesmanagement.entity.DroneEntity;
import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.enums.DroneModelEnum;
import com.ie.dronesmanagement.enums.DroneStateEnum;
import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.mapper.DroneMapper;
import com.ie.dronesmanagement.mapper.MedicationMapper;
import com.ie.dronesmanagement.model.BatteryCapacityResponse;
import com.ie.dronesmanagement.model.CreateDroneRequestDto;
import com.ie.dronesmanagement.model.DroneResponseDto;
import com.ie.dronesmanagement.model.LoadDroneRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;
import com.ie.dronesmanagement.repository.DroneRepository;
import com.ie.dronesmanagement.repository.MedicationRepository;
import com.ie.dronesmanagement.util.Constants;

@SpringBootTest
@AutoConfigureMockMvc
class DroneServiceImplTest {

	@MockBean
	DroneRepository droneRepository;

	@MockBean
	MedicationRepository medicationRepository;

	@MockBean
	DroneMapper droneMapper;

	@MockBean
	MedicationMapper medicationMapper;

	@Autowired
	DroneServiceImpl droneServiceImpl;

	@Test
	void test_getAllDrones_success() {
		when(droneRepository.findAll()).thenReturn(buildDroneEntityList());
		when(droneMapper.toDroneResponseList(any())).thenReturn(buildDroneResponseList());
		List<DroneResponseDto> droneList = droneServiceImpl.getAllDrones();
		assertNotNull(droneList);
		assertEquals(1, droneList.size());
		assertEquals(100, droneList.get(0).getBatteryCapacity());
	}

	@Test
	void test_getDroneBySerialNumber_success() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		DroneResponseDto droneResponseDto = droneServiceImpl.getDroneBySerialNumber("12345");
		assertNotNull(droneResponseDto);
		assertEquals(400, droneResponseDto.getWeightLimit());
		assertEquals(DroneStateEnum.IDLE, droneResponseDto.getState());
		assertEquals(DroneModelEnum.CRUISER_WEIGHT, droneResponseDto.getModel());
		assertEquals(100, droneResponseDto.getBatteryCapacity());
	}

	@Test
	void test_getDroneBySerialNumber_error_serialNumberNotFound() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.getDroneBySerialNumber("12345"));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_registerDrone_success() {
		CreateDroneRequestDto createDroneRequestDto = new CreateDroneRequestDto("12345",
				DroneModelEnum.CRUISER_WEIGHT.name(), 400, 100, DroneStateEnum.IDLE.name());
		when(droneRepository.save(any())).thenReturn(buildDroneEntity());
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(droneMapper.fromCreateDroneRequestDto(any())).thenReturn(buildDroneEntity());
		DroneResponseDto droneResponseDto = droneServiceImpl.registerDrone(createDroneRequestDto);
		assertNotNull(droneResponseDto);
		assertEquals(400, droneResponseDto.getWeightLimit());
		assertEquals(DroneStateEnum.IDLE, droneResponseDto.getState());
		assertEquals(DroneModelEnum.CRUISER_WEIGHT, droneResponseDto.getModel());
		assertEquals(100, droneResponseDto.getBatteryCapacity());
	}

	@Test
	void test_registerDrone_error_serialNumberAlreadyExists() {
		DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException(
				"duplicate");
		when(droneRepository.save(any())).thenThrow(dataIntegrityViolationException);
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(droneMapper.fromCreateDroneRequestDto(any())).thenReturn(buildDroneEntity());
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.registerDrone(new CreateDroneRequestDto()));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_updateDrone_success() {
		UpdateDroneRequestDto updateDroneRequestDto = new UpdateDroneRequestDto("12345",
				DroneModelEnum.CRUISER_WEIGHT.name(), 400, 100, DroneStateEnum.IDLE.name());
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		when(droneRepository.save(any())).thenReturn(buildDroneEntity());
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(droneMapper.fromCreateDroneRequestDto(any())).thenReturn(buildDroneEntity());
		DroneResponseDto droneResponseDto = droneServiceImpl.updateDrone("12345", updateDroneRequestDto);
		assertNotNull(droneResponseDto);
		assertEquals(400, droneResponseDto.getWeightLimit());
		assertEquals(DroneStateEnum.IDLE, droneResponseDto.getState());
		assertEquals(DroneModelEnum.CRUISER_WEIGHT, droneResponseDto.getModel());
		assertEquals(100, droneResponseDto.getBatteryCapacity());
	}

	@Test
	void test_updateDrone_error_serialNumberAlreadyExists() {
		DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException(
				"duplicate");
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		when(droneRepository.save(any())).thenThrow(dataIntegrityViolationException);
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(droneMapper.fromCreateDroneRequestDto(any())).thenReturn(buildDroneEntity());
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.updateDrone("12345", new UpdateDroneRequestDto(null, null, 100, null, null)));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_updateDrone_error_serialNumberNotFound() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.updateDrone("12345", new UpdateDroneRequestDto(null, null, 100, null, null)));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_deleteDrone_success() {
		when(droneRepository.deleteBySerialNumber(any())).thenReturn(Long.valueOf(1));
		assertDoesNotThrow(() -> droneServiceImpl.deleteDrone("12345"));
	}

	@Test
	void test_deleteDrone_error_serialNumberNotFound() {
		when(droneRepository.deleteBySerialNumber(any())).thenReturn(Long.valueOf(0));
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.deleteDrone("12345"));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_getAvailableDronesForLoading_success() {
		when(droneRepository.findByState(any())).thenReturn(buildDroneEntityList());
		when(droneMapper.toDroneResponseList(any())).thenReturn(buildDroneResponseList());
		List<DroneResponseDto> droneList = droneServiceImpl.getAvailableDronesForLoading();
		assertNotNull(droneList);
		assertEquals(1, droneList.size());
		assertEquals(100, droneList.get(0).getBatteryCapacity());
	}

	@Test
	void test_getDroneBatteryCapacity_success() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		BatteryCapacityResponse batteryCapacityResponse = droneServiceImpl.getDroneBatteryCapacity("12345");
		assertNotNull(batteryCapacityResponse);
		assertEquals(100, batteryCapacityResponse.getBatteryCapacity());
	}

	@Test
	void test_getDroneBatteryCapacity_error_serialNumberNotFound() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.getDroneBatteryCapacity("12345"));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_getLoadedMedications_success() {
		DroneEntity droneEntity = buildDroneEntity();
		droneEntity.setMedications(buildMedicationEntityList());
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(droneEntity));
		when(medicationMapper.toMedicationResponseList(any())).thenReturn(buildMedicationsList());
		List<MedicationResponseDto> medicationResponseDtos = droneServiceImpl.getLoadedMedications("12345");
		assertNotNull(medicationResponseDtos);
		assertEquals(1, medicationResponseDtos.size());
		assertEquals("medication1", medicationResponseDtos.get(0).getName());
		assertEquals(50, medicationResponseDtos.get(0).getWeight());
		assertEquals("MED1", medicationResponseDtos.get(0).getCode());
	}

	@Test
	void test_getLoadedMedications_error_droneIsNotLoaded() {
		DroneEntity droneEntity = buildDroneEntity();
		droneEntity.setMedications(new ArrayList<>());
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(droneEntity));
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.getLoadedMedications("12345"));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_IS_NOT_LOADED_WITH_MEDICATIONS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_IS_NOT_LOADED_WITH_MEDICATIONS_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_getLoadedMedications_error_serialNumberNotFound() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class,
				() -> droneServiceImpl.getLoadedMedications("12345"));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_loadDrone_success() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		when(medicationRepository.findByCodeIn(any())).thenReturn(buildMedicationEntityList());
		when(medicationMapper.toMedicationResponseList(any())).thenReturn(buildMedicationsList());
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(medicationRepository.findByCode(any())).thenReturn(Optional.of(buildMedicationEntity()))
				.thenReturn(Optional.of(buildMedicationEntity1()));
		DroneResponseDto droneResponseDto = assertDoesNotThrow(() -> droneServiceImpl.loadDrone("12345",
				new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(droneResponseDto);
	}

	@Test
	void test_loadDrone_error_medicationIsLoadedByOtherDrone() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		when(medicationRepository.findByCodeIn(any())).thenReturn(buildMedicationEntityList());
		when(medicationMapper.toMedicationResponseList(any())).thenReturn(buildMedicationsList());
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(medicationRepository.findByCode(any())).thenReturn(Optional.of(buildMedicationEntity()));
		BackendException backendException = assertThrows(BackendException.class, () -> droneServiceImpl
				.loadDrone("12345", new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_loadDrone_error_medicationsExceedWeightLimit() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		List<MedicationEntity> medications = buildMedicationEntityList();
		medications.add(new MedicationEntity(Long.valueOf(20), "medication1", 600, "M_12345678", null, null));
		when(medicationRepository.findByCodeIn(any())).thenReturn(medications);

		BackendException backendException = assertThrows(BackendException.class, () -> droneServiceImpl
				.loadDrone("12345", new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_HAS_EXCEEDED_THE_WEIGHT_LIMIT_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_HAS_EXCEEDED_THE_WEIGHT_LIMIT_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_loadDrone_error_droneStateNotIdle() {
		DroneEntity droneEntity = buildDroneEntity();
		droneEntity.setState(DroneStateEnum.DELIVERING);
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(droneEntity));

		BackendException backendException = assertThrows(BackendException.class, () -> droneServiceImpl
				.loadDrone("12345", new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_loadDrone_error_droneBatteryLessThan25() {
		DroneEntity droneEntity = buildDroneEntity();
		droneEntity.setBatteryCapacity(20);
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(droneEntity));

		BackendException backendException = assertThrows(BackendException.class, () -> droneServiceImpl
				.loadDrone("12345", new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_BATTER_CAPACITY_IS_BELOW_25_PERCENT_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_BATTER_CAPACITY_IS_BELOW_25_PERCENT_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_loadDrone_error_medicationCodeIsNotFound() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.of(buildDroneEntity()));
		when(medicationRepository.findByCodeIn(any())).thenReturn(buildMedicationEntityList());
		when(medicationMapper.toMedicationResponseList(any())).thenReturn(buildMedicationsList());
		when(droneMapper.toDroneResponseDto(any())).thenReturn(buildDroneResponseDto());
		when(medicationRepository.findByCode(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class, () -> droneServiceImpl
				.loadDrone("12345", new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(backendException);
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getReason());
	}

	@Test
	void test_loadDrone_error_serialNumberNotFound() {
		when(droneRepository.findBySerialNumber(any())).thenReturn(Optional.empty());
		BackendException backendException = assertThrows(BackendException.class, () -> droneServiceImpl
				.loadDrone("12345", new LoadDroneRequestDto(Arrays.asList("A123456789", "B12345678"))));

		assertNotNull(backendException);
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, backendException.getMessage());
	}

	private List<DroneEntity> buildDroneEntityList() {
		List<DroneEntity> droneEntityList = new ArrayList<>();
		droneEntityList.add(buildDroneEntity());
		return droneEntityList;
	}

	private DroneEntity buildDroneEntity() {
		DroneEntity droneEntity = new DroneEntity();
		droneEntity.setBatteryCapacity(100);
		droneEntity.setModel(DroneModelEnum.CRUISER_WEIGHT);
		droneEntity.setState(DroneStateEnum.IDLE);
		droneEntity.setWeightLimit(500);
		return droneEntity;
	}

	private List<DroneResponseDto> buildDroneResponseList() {
		List<DroneResponseDto> dronesList = new ArrayList<>();
		DroneResponseDto drone = new DroneResponseDto("12345", DroneModelEnum.CRUISER_WEIGHT, 400, 100,
				DroneStateEnum.IDLE, null);
		dronesList.add(drone);
		return dronesList;
	}

	private DroneResponseDto buildDroneResponseDto() {
		return new DroneResponseDto("12345", DroneModelEnum.CRUISER_WEIGHT, 400, 100, DroneStateEnum.IDLE, null);
	}

	private MedicationEntity buildMedicationEntity() {
		MedicationEntity medicationEntity = new MedicationEntity();
		medicationEntity.setCode("A123456789");
		medicationEntity.setImageAsBase64("image");
		medicationEntity.setName("Fucidin");
		medicationEntity.setWeight(100);
		return medicationEntity;
	}

	private MedicationEntity buildMedicationEntity1() {
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

	private List<MedicationResponseDto> buildMedicationsList() {
		List<MedicationResponseDto> medicationResponseDtos = new ArrayList<>();
		MedicationResponseDto medicationResponseDto = new MedicationResponseDto("medication1", 50, "MED1", null);
		medicationResponseDtos.add(medicationResponseDto);
		return medicationResponseDtos;
	}
}
