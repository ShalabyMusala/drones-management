package com.ie.dronesmanagement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity
@Table(name = "drone")
public class DroneEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "serial_number cannot be null or empty")
	@Size(max = 100)
	@Column(name = "serial_number", unique = true)
	private String serialNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "model")
	private DroneModelEnum model;

	@NotNull(message = "weight_limit cannot be null or empty")
	@Column(name = "weight_limit")
	@Max(value = 500)
	private Integer weightLimit;

	@NotNull(message = "battery_capacity cannot be null or empty")
	@Range(min = 0, max = 100)
	@Column(name = "battery_capacity")
	private Integer batteryCapacity;

	@NotNull(message = "state cannot be null or empty")
	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private DroneStateEnum state;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "drone", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<MedicationEntity> medications;
}
