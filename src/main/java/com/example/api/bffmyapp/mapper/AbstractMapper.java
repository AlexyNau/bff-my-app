package com.example.api.bffmyapp.mapper;

import javassist.NotFoundException;
import org.mapstruct.MappingTarget;

public interface AbstractMapper<T, DTO> {
	DTO toDTO(T model) throws NotFoundException;
	T toModel(@MappingTarget T model, DTO dto);
}