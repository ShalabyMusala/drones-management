package com.ie.dronesmanagement.util;

import org.springframework.http.HttpStatus;

import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;

public class ValidationUtil {

	public static void validateUpdateDroneRequestDto(UpdateDroneRequestDto updateDroneRequestDto) {
		if (updateDroneRequestDto.getBatteryCapacity() == null
				&& updateDroneRequestDto.getModel() == null & updateDroneRequestDto.getWeightLimit() == null
				&& updateDroneRequestDto.getState() == null && updateDroneRequestDto.getSerialNumber() == null) {
			throw new BackendException(Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE,
					Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_CODE, Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE,
					HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	public static void validateUpdateMedicationRequestDto(UpdateMedicationRequestDto updateMedicationRequestDto) {
		if (updateMedicationRequestDto.getCode() == null
				&& updateMedicationRequestDto.getImageAsBase64() == null & updateMedicationRequestDto.getName() == null
				&& updateMedicationRequestDto.getWeight() == null) {
			throw new BackendException(Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE,
					Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_CODE, Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE,
					HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}
}
