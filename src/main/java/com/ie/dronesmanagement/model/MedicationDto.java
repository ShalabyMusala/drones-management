package com.ie.dronesmanagement.model;

import com.ie.dronesmanagement.entity.DroneEntity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto {

	@Pattern(regexp = "^[a-zA-Z0-9_.-]*$")
	
	private String name;

	private Float weight;

	@Pattern(regexp = "^[A-Z0-9_]{10}$")
	private String code;

	private String imageAsBase64;
}
