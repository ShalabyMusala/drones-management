package com.ie.dronesmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ie.dronesmanagement.entity.MedicationEntity;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {

}
