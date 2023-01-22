package com.ie.dronesmanagement.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medication")
public class MedicationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "name cannot be null or empty")
	@Pattern(regexp = "^[a-zA-Z0-9_.-]*$")
	@Column(name = "name")
	private String name;

	@NotNull(message = "weight cannot be null or empty")
	@Column(name = "weight")
	private Integer weight;

	@NotBlank(message = "code cannot be null or empty")
	@Pattern(regexp = "^[A-Z0-9_]{10}$")
	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "image")
	private String imageAsBase64;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "drone_id")
	@JsonBackReference
	private DroneEntity drone;
}
