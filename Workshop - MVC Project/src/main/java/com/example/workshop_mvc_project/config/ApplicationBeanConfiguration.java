package com.example.workshop_mvc_project.config;

import com.example.workshop_mvc_project.model.dto.ExportEmployeeDto;
import com.example.workshop_mvc_project.model.entity.Employee;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper;
    }
}
