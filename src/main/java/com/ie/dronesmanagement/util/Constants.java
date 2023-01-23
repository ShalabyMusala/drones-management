package com.ie.dronesmanagement.util;

public class Constants {

	public static final String INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_MESSAGE = "Please provide at least one field to update";
	public static final int INVALID_UPDATE_DUE_TO_NULL_FIELDS_ERROR_CODE = 6001;

	public static final String MEDICATION_CODE_IS_NOT_FOUND_ERROR_MESSAGE = "Medication code is not found";
	public static final int MEDICATION_CODE_IS_NOT_FOUND_ERROR_CODE = 6002;

	public static final String DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_MESSAGE = "Drone serial number is not found";
	public static final int DRONE_SERIAL_NUMBER_IS_NOT_FOUND_ERROR_CODE = 6003;

	public static final String MEDICATION_CODE_ALREADY_EXISTS_ERROR_MESSAGE = "Medication code is already exists";
	public static final int MEDICATION_CODE_ALREADY_EXISTS_ERROR_CODE = 6004;

	public static final String DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_MESSAGE = "Drone serial number is already exists";
	public static final int DRONE_SERIAL_NUMBER_ALREADY_EXISTS_ERROR_CODE = 6005;

	public static final String DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_MESSAGE = "Drone is not available for loading";
	public static final int DRONE_IS_NOT_AVAILABLE_FOR_LOADING_ERROR_CODE = 6006;

	public static final String MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_MESSAGE = "Medication is already loaded by other drone";
	public static final int MEDICATION_IS_ALREADY_LOADED_BY_OTHER_DRONE_ERROR_CODE = 6007;

	public static final String DRONE_HAS_EXCEEDED_THE_WEIGHT_LIMIT_ERROR_MESSAGE = "Medications weight exceed the weight limit for the drone";
	public static final int DRONE_HAS_EXCEEDED_THE_WEIGHT_LIMIT_ERROR_CODE = 6008;
	
	public static final String DRONE_BATTER_CAPACITY_IS_BELOW_25_PERCENT_ERROR_MESSAGE = "Drone battery is below 25% where it cannot be loaded right now";
	public static final int DRONE_BATTER_CAPACITY_IS_BELOW_25_PERCENT_ERROR_CODE = 6009;
	
	public static final String DRONE_IS_NOT_LOADED_WITH_MEDICATIONS_ERROR_MESSAGE = "Drone is not loaded with medications";
	public static final int DRONE_IS_NOT_LOADED_WITH_MEDICATIONS_ERROR_CODE = 6010;
	
	public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Unprocessable entity";
	public static final int UNPROCESSABLE_ENTITY_CODE = 4022;
	
	public static final String SUCCESS_MESSAGE = "Success";
	public static final int SUCCESS_CODE = 2000;
}
