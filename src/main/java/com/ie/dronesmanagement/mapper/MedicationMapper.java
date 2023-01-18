package com.ie.dronesmanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.model.MedicationResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicationMapper {

	List<MedicationResponseDto> toMedicationResponseList(List<MedicationEntity> medicationEntityList);

	MedicationResponseDto toMedicationResponseDto(MedicationEntity medicationEntity);
}
