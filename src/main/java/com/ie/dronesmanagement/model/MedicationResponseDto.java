package com.ie.dronesmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationResponseDto {

	private String name;
	private Float weight;
	private String code;
	private String imageAsBase64;

}
