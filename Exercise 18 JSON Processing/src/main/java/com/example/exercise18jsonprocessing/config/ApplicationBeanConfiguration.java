package com.example.exercise18jsonprocessing.config;

import com.example.exercise18jsonprocessing.model.dto.CustomerWithTotalSalesDto;
import com.example.exercise18jsonprocessing.model.entity.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> stringToLocalDateConverter = new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

                return LocalDate.parse(mappingContext.getSource(), format);
            }
        };

        modelMapper.addConverter(stringToLocalDateConverter);

        modelMapper
                .typeMap(Customer.class, CustomerWithTotalSalesDto.class)
                .addMappings(mapper -> mapper.map(Customer::getName, CustomerWithTotalSalesDto::setFullName));


//        modelMapper
//                .typeMap(User.class, UsersFirstAndLastNameAgeDto.class)
//                .addMappings(mapper ->
//                        mapper.map(User::getSoldProducts, UsersFirstAndLastNameAgeDto::setSoldProducts));

        return modelMapper;
    }

    @Bean
    public Gson gson() {

        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
}
