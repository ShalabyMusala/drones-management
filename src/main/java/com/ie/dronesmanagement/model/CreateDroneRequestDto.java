package com.ie.dronesmanagement.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class CreateDroneRequestDto {

	@NotNull(message = "serialNumber cannot be null or empty")
	@Size(max = 100)
	private String serialNumber;

	@NotBlank(message = "model cannot be null or empty")
	@IsValueOfEnum(enumClass = DroneModelEnum.class, message = "model contain value that doesnot exist in Drone Model Enum")
	private String model;

	@NotNull(message = "weightLimit cannot be null or empty")
	@Max(value = 500, message = "weight limit cannot exceed 500gr")
	private Integer weightLimit;

	@NotNull(message = "batteryCapacity cannot be null or empty")
	@Range(min = 0, max = 100, message = "batteryCapacity should be in range(0,100)")
	private Integer batteryCapacity;

	@NotBlank(message = "state cannot be null or empty")
	@IsValueOfEnum(enumClass = DroneStateEnum.class, message = "state contain value that doesnot exist in Drone State Enum")
	private String state;

}
