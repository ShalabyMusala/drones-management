package com.ie.dronesmanagement.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ie.dronesmanagement.entity.DroneEntity;
import com.ie.dronesmanagement.entity.MedicationEntity;
import com.ie.dronesmanagement.model.CreateDroneRequestDto;
import com.ie.dronesmanagement.model.DroneResponseDto;
import com.ie.dronesmanagement.model.MedicationResponseDto;
import com.ie.dronesmanagement.model.UpdateDroneRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DroneMapper {

	List<DroneResponseDto> toDroneResponseList(List<DroneEntity> droneEntityList);

	DroneResponseDto toDroneResponseDto(DroneEntity droneEntity);

	DroneEntity fromCreateDroneRequestDto(CreateDroneRequestDto createDroneRequestDto);

	DroneEntity fromUpdateDroneRequestDto(UpdateDroneRequestDto updateDroneRequestDto);

	List<MedicationResponseDto> toMedicationResponseList(List<MedicationEntity> medicationEntityList);

	MedicationResponseDto toMedicationResponseDto(MedicationEntity medicationEntity);

}
