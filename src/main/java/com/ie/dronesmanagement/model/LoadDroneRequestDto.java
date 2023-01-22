package com.ie.dronesmanagement.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoadDroneRequestDto {

	@NotNull(message = "Medications list should not be null")
	@NotEmpty(message = "Medications list should not be empty")
	private List<String> medicationCodeList;
}
