package com.ie.dronesmanagement.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class DroneHistoryResponseDto {
	private String serialNumber;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	private DroneStateEnum state;
	private Integer batteryCapacity;
	
}
