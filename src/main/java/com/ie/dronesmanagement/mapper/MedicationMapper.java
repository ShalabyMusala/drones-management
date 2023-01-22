package com.ie.dronesmanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.model.CreateMedicationRequestDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateMedicationRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicationMapper {

	List<MedicationResponseDto> toMedicationResponseList(List<MedicationEntity> medicationEntityList);

	MedicationResponseDto toMedicationResponseDto(MedicationEntity medicationEntity);
	
	MedicationEntity fromCreateMedicationRequestDto(CreateMedicationRequestDto createMedicationRequestDto);
	
	MedicationEntity fromUpdateMedicationRequestDto(UpdateMedicationRequestDto updateMedicationRequestDto);
}
