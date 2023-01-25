package com.ie.dronesmanagement.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.ie.dronesmanagement.exception.BackendException;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;
import com.ie.dronesmanagement.util.Constants;
import com.ie.dronesmanagement.util.ValidationUtil;

@SpringBootTest
@AutoConfigureMockMvc
class ValidationUtilTest {

	@Test
	void test_validateUpdateDroneRequestDto_valid() {
		assertDoesNotThrow(() -> ValidationUtil
				.validateUpdateDroneRequestDto(new UpdateDroneRequestDto(null, null, 100, null, null)));
	}

	@Test
	void test_validateUpdateDroneRequestDto_error_invalid() {
		BackendException backendException = assertThrows(BackendException.class, () -> ValidationUtil
				.validateUpdateDroneRequestDto(new UpdateDroneRequestDto(null, null, null, null, null)));
		assertNotNull(backendException);
		assertEquals(Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE, backendException.getMessage());
	}

	@Test
	void test_validateUpdateMedicationRequestDto_valid() {
		assertDoesNotThrow(() -> ValidationUtil
				.validateUpdateMedicationRequestDto(new UpdateMedicationRequestDto(null, 50, null, null)));
	}

	@Test
	void test_validateUpdateMedicationRequestDto_error_invalid() {
		BackendException backendException = assertThrows(BackendException.class, () -> ValidationUtil
				.validateUpdateMedicationRequestDto(new UpdateMedicationRequestDto(null, null, null, null)));
		assertNotNull(backendException);
		assertEquals(Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_CODE, backendException.getCode());
		assertEquals(Constants.INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE, backendException.getMessage());
	}
}
