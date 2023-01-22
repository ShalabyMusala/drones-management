package com.ie.dronesmanagement.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ie.dronesmanagement.entity.DroneEntity;
import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.enums.DroneModelEnum;
import com.ie.dronesmanagement.enums.DroneStateEnum;
import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.mapper.DroneMapper;
import com.ie.dronesmanagement.model.CreateDroneRequestDto;
import com.ie.dronesmanagement.model.DroneResponseDto;
import com.ie.dronesmanagement.model.LoadDroneRequestDto;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;
import com.ie.dronesmanagement.repository.DroneRepository;
import com.ie.dronesmanagement.repository.MedicationRepository;
import com.ie.dronesmanagement.service.DroneService;
import com.ie.dronesmanagement.util.Constants;
import com.ie.dronesmanagement.util.ValidationUtil;

@Service
@Transactional
public class DroneServiceImpl implements DroneService {

	private final DroneRepository droneRepository;
	private final MedicationRepository medicationRepository;
	private final DroneMapper droneMapper;

	@Autowired
	public DroneServiceImpl(DroneRepository droneRepository, MedicationRepository medicationRepository,
			DroneMapper droneMapper) {
		this.droneRepository = droneRepository;
		this.medicationRepository = medicationRepository;
		this.droneMapper = droneMapper;
	}

	@Override
	public List<DroneResponseDto> getAllDrones() {
		return droneMapper.toDroneResponseList(droneRepository.findAll());
	}

	@Override
	public DroneResponseDto getDroneBySerialNumber(String serialNumber) throws BackendException {
		DroneEntity droneEntity = droneRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new BackendException(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						HttpStatus.UNPROCESSABLE_ENTITY.value()));
		return droneMapper.toDroneResponseDto(droneEntity);
	}

	@Override
	public DroneResponseDto registerDrone(CreateDroneRequestDto createDroneRequestDto) throws BackendException {
		try {
			return droneMapper.toDroneResponseDto(
					droneRepository.save(droneMapper.fromCreateDroneRequestDto(createDroneRequestDto)));
		} catch (DataIntegrityViolationException ex) {
			throw new BackendException(ex.getMessage(), Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_CODE,
					Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_MESSAGE,
					HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	@Override
	public DroneResponseDto updateDrone(String serialNumber, UpdateDroneRequestDto updateDroneRequestDto)
			throws BackendException {
		ValidationUtil.validateUpdateDroneRequestDto(updateDroneRequestDto);
		DroneEntity droneEntity = droneRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new BackendException(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						HttpStatus.UNPROCESSABLE_ENTITY.value()));
		if (StringUtils.isNotBlank(updateDroneRequestDto.getModel())) {
			droneEntity.setModel(DroneModelEnum.valueOf(updateDroneRequestDto.getModel()));
		}
		if (updateDroneRequestDto.getWeightLimit() != null) {
			droneEntity.setWeightLimit(updateDroneRequestDto.getWeightLimit());
		}
		if (updateDroneRequestDto.getBatteryCapacity() != null) {
			droneEntity.setBatteryCapacity(updateDroneRequestDto.getBatteryCapacity());
		}
		if (StringUtils.isNotBlank(updateDroneRequestDto.getState())) {
			droneEntity.setState(DroneStateEnum.valueOf(updateDroneRequestDto.getState()));
		}
		if (StringUtils.isNotBlank(updateDroneRequestDto.getSerialNumber())) {
			droneEntity.setSerialNumber(updateDroneRequestDto.getSerialNumber());
		}
		try {
			droneRepository.save(droneEntity);
		} catch (DataIntegrityViolationException ex) {
			throw new BackendException(ex.getMessage(), Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_CODE,
					Constants.DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_MESSAGE,
					HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
		return droneMapper.toDroneResponseDto(droneEntity);
	}

	@Override
	public void deleteDrone(String serialNumber) throws BackendException {
		Long deleted = droneRepository.deleteBySerialNumber(serialNumber);
		if (deleted == 0) {
			throw new BackendException(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
					Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE,
					Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	@Override
	public List<DroneResponseDto> getAvailableDronesForLoading() {
		List<DroneEntity> idleDroneEntityList = droneRepository.findByState(DroneStateEnum.IDLE);
		return droneMapper.toDroneResponseList(idleDroneEntityList);
	}

	@Override
	public DroneResponseDto loadDrone(String serialNumber, LoadDroneRequestDto loadDroneRequestDto) {
		DroneEntity droneEntity = droneRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new BackendException(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						HttpStatus.UNPROCESSABLE_ENTITY.value()));
		if (!DroneStateEnum.IDLE.equals(droneEntity.getState())) {
			throw new BackendException(Constants.DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_MESSAGE,
					Constants.DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_CODE,
					Constants.DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_MESSAGE,
					HttpStatus.UNPROCESSABLE_ENTITY.value());
		}

		loadDroneRequestDto.getMedicationCodeList().stream().forEach(medicationCode -> {
			MedicationEntity medicationEntity = medicationRepository.findByCode(medicationCode)
					.orElseThrow(() -> new BackendException(
							Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE + ":" + medicationCode,
							Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE,
							Constants.MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE,
							HttpStatus.UNPROCESSABLE_ENTITY.value()));
			if (medicationEntity.getDrone() != null) {
				throw new BackendException(Constants.MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_MESSAGE + ":",
						Constants.MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_CODE,
						Constants.MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_MESSAGE,
						HttpStatus.UNPROCESSABLE_ENTITY.value());
			}
			medicationEntity.setDrone(droneEntity);
			medicationRepository.save(medicationEntity);
		});
		DroneEntity loadedDroneEntity = droneRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new BackendException(Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE,
						Constants.DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE,
						HttpStatus.UNPROCESSABLE_ENTITY.value()));

		return droneMapper.toDroneResponseDto(loadedDroneEntity);
	}

}