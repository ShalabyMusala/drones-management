package com.ie.dronesmanagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ie.dronesmanagement.enums.DroneModelEnum;
import com.ie.dronesmanagement.enums.DroneStateEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneResponseDto {

	private String serialNumber;
	private DroneModelEnum model;
	private Integer weightLimit;
	private Integer batteryCapacity;
	private DroneStateEnum state;
	@JsonInclude(Include.NON_EMPTY)
	private List<MedicationResponseDto> medications;

}
