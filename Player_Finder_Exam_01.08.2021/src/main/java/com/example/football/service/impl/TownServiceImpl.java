package com.example.football.service.impl;

import com.example.football.models.dto.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidatioUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {

    public static final String TOWNS_FILE = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final ValidatioUtil validatioUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ValidatioUtil validatioUtil, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.validatioUtil = validatioUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE));
    }

    @Override
    public String importTowns() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTownsFileContent(), TownSeedDto[].class))
                .filter(townSeedDto -> {
                    boolean isValid = validatioUtil.isValid(townSeedDto)
                            && !isTownExist(townSeedDto.getName());

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported %s Moscow - %d",
                                    townSeedDto.getName(), townSeedDto.getPopulation())
                                    : "Invalid Town")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(townSeedDto -> modelMapper.map(townSeedDto, Town.class))
                .forEach(townRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public Town findByName(String name) {
        return townRepository.findByName(name);
    }

    @Override
    public boolean isTownExist(String name) {

        return townRepository.existsByName(name);
    }
}
