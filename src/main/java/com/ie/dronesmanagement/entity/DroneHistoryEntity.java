package com.ie.dronesmanagement.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "dronehistory")
public class DroneHistoryEntity {
	@CreatedDate
	@Column(name = "date_created")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "date_modified")
	private LocalDateTime updatedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "state")
	private DroneStateEnum state;

	@Column(name = "battery_capacity")
	private Integer batteryCapacity;

	@Column(name = "drone_id")
	private Long droneId;

	@Column(name = "drone_serial_number")
	private String droneSerialNumber;
}
