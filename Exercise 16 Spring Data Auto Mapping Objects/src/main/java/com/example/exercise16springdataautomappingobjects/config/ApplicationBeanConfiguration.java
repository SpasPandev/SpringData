package com.example.exercise16springdataautomappingobjects.config;

import com.example.exercise16springdataautomappingobjects.model.dto.GameAddDto;
import com.example.exercise16springdataautomappingobjects.model.entity.Game;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(GameAddDto.class, Game.class)
                .addMappings(mapper ->
                        mapper.map(GameAddDto::getThumbnailURL, Game::setImageThumbnail));

        return modelMapper;

//        return new ModelMapper();
    }
}
