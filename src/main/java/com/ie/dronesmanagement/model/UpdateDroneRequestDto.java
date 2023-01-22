package com.ie.dronesmanagement.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.ie.dronesmanagement.annotations.IsValueOfEnum;
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
public class UpdateDroneRequestDto {

	@Size(max = 100)
	private String serialNumber;

	@IsValueOfEnum(enumClass = DroneModelEnum.class, message = "model contain value that doesnot exist in Drone Model Enum")
	private String model;

	private Integer weightLimit;

	@Range(min = 0, max = 100, message = "batteryCapacity should be in range(0,100)")
	private Integer batteryCapacity;

	@IsValueOfEnum(enumClass = DroneStateEnum.class, message = "state contain value that doesnot exist in Drone State Enum")
	private String state;
}
