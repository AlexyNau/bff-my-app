package com.example.api.bffmyapp.configuration;

import org.mapstruct.MapperConfig;

@MapperConfig(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring")
public interface MapStructDefaultConfig {

}