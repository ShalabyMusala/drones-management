package com.ie.dronesmanagement.entity;

import java.util.List;
import java.util.UUID;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
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
