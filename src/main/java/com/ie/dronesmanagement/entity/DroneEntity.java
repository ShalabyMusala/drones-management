package com.ie.dronesmanagement.entity;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ie.dronesmanagement.enums.DroneModelEnum;
import com.ie.dronesmanagement.enums.DroneStateEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
	@Column(name = "id")
	private Long id;

	@GenericGenerator(name = "uuid", strategy = "uuid4")
	@Size(max = 100)
	@Column(name = "serial_number")
	private UUID serialNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "model")
	private DroneModelEnum model;

	@Column(name = "weight_limit")
	private Float weightLimit;

	@Range(min = 0, max = 100)
	@Column(name = "battery_capacity")
	private Float batteryCapacity;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private DroneStateEnum state;

	@OneToMany(mappedBy = "drone", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<MedicationEntity> medications;
}
