package com.ie.dronesmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
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
	@Column(name = "id")
	private Long id;

	@Pattern(regexp = "^[a-zA-Z0-9_.-]*$")
	@Column(name = "name")
	private String name;

	@Column(name = "weight")
	private Float weight;

	@Pattern(regexp = "^[A-Z0-9_]{10}$")
	@Column(name = "code")
	private String code;

	@Column(name = "image")
	private String imageAsBase64;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "drone_id")
	@JsonBackReference
	private DroneEntity drone;
}
