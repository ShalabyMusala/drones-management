package com.ie.dronesmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ie.dronesmanagement.entity.MedicationEntity;

public interface MedicationRepository extends JpaRepository<MedicationEntity, Long> {

	Optional<MedicationEntity> findByCode(String code);

	Long deleteByCode(String code);
	
	List<MedicationEntity> findByCodeIn(List<String> codeList);
}
