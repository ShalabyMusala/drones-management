package com.ie.dronesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ie.dronesmanagement.entity.DroneHistoryEntity;

public interface DroneHistoryRepository extends JpaRepository<DroneHistoryEntity, Long> {

}
