package com.kovaliv.lab4.config;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return addConverters(modelMapper);
    }

    private ModelMapper addConverters(ModelMapper modelMapper) {
        return modelMapper;
    }
}
