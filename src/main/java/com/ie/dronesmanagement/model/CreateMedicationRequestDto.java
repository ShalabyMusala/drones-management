package com.ie.dronesmanagement.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicationRequestDto {

	@Pattern(regexp = "^[a-zA-Z0-9_.-]*$")
	@NotBlank(message = "medication name cannot be null or empty")
	private String name;

	@NotNull(message = "medication weight cannot be null")
	private Float weight;

	@Pattern(regexp = "^[A-Z0-9_]{10}$")
	@NotBlank(message = "medication code cannot be null or empty")
	private String code;

	private String imageAsBase64;
}
