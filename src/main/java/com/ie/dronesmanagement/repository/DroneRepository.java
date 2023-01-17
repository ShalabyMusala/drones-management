package com.ie.dronesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ie.dronesmanagement.entity.DroneEntity;

public interface DroneRepository extends JpaRepository<DroneEntity, Long> {

}
