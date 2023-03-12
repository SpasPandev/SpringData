package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    public static final String TEAMS_FILE = "src/main/resources/files/json/teams.json";
    private final TeamRepository teamRepository;
    private final TownService townService;
    private final ValidatioUtil validatioUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public TeamServiceImpl(TeamRepository teamRepository, TownService townService, ValidatioUtil validatioUtil, ModelMapper modelMapper, Gson gson) {
        this.teamRepository = teamRepository;
        this.townService = townService;
        this.validatioUtil = validatioUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE));
    }

    @Override
    public String importTeams() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamSeedDto[].class))
                .filter(teamSeedDto -> {
                    boolean isValid = validatioUtil.isValid(teamSeedDto)
                            && !isTeamNameExist(teamSeedDto.getName());

                    stringBuilder.append(isValid
                            ? String.format("Successfully imported Team %s - %d", teamSeedDto.getName(),
                            teamSeedDto.getFanBase())
                            : "Invalid Team")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(teamSeedDto -> {
                    Team team = modelMapper.map(teamSeedDto, Team.class);
                    team.setTown(townService.findByName(teamSeedDto.getTownName()));

                    return team;
                })
                .forEach(teamRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isTeamNameExist(String name) {
        return teamRepository.existsByName(name);
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findByName(name);
    }
}
