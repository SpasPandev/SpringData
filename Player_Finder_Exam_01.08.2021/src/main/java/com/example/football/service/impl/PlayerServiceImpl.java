package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidatioUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    public static final String PLAYERS_FILE = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;
    private final ModelMapper modelMapper;
    private final ValidatioUtil validatioUtil;
    private final XmlParser xmlParser;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownService townService, TeamService teamService, StatService statService, ModelMapper modelMapper, ValidatioUtil validatioUtil, XmlParser xmlParser) {
        this.playerRepository = playerRepository;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
        this.modelMapper = modelMapper;
        this.validatioUtil = validatioUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_FILE));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(PLAYERS_FILE, PlayerSeedRootDto.class)
                .getPlayers()
                .stream()
                .filter(playerSeedDto -> {
                    boolean isValid = validatioUtil.isValid(playerSeedDto)
                            && !isEmailExist(playerSeedDto.getEmail())
                            && townService.isTownExist(playerSeedDto.getTown().getName())
                            && teamService.isTeamNameExist(playerSeedDto.getTeam().getName());

                    stringBuilder
                            .append(isValid
                                    ? String.format("Successfully imported Player %s %s - %s",
                                    playerSeedDto.getFirstName(), playerSeedDto.getLastName(),
                                    playerSeedDto.getPosition().name())
                                    : "Invalid Player")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(playerSeedDto -> {
                    Player player = modelMapper.map(playerSeedDto, Player.class);
                    player.setTown(townService.findByName(playerSeedDto.getTown().getName()));
                    player.setTeam(teamService.findByName(playerSeedDto.getTeam().getName()));
                    player.setStat(statService.findById(playerSeedDto.getStat().getId()));

                    return player;
                })
                .forEach(playerRepository::save);

        return stringBuilder.toString();
    }

    private boolean isEmailExist(String email) {
        return playerRepository.existsByEmail(email);
    }

    @Override
    public String exportBestPlayers() {

        StringBuilder stringBuilder = new StringBuilder();

        List<Player> players = playerRepository
                .findAllPlayersOrderByShootingDescPassingDescEnduranceDescAndPlayerLastNameBornBetween(
                        LocalDate.of(1995, 1, 1),
                        LocalDate.of(2003, 1, 1));

        players
                .forEach(player -> stringBuilder
                        .append(String.format("""
                                        Player - %s %s
                                        \tPosition - %s
                                        \tTeam - %s
                                        \tStadium - %s
                                        """, player.getFirstName(), player.getLastName(),
                                player.getPosition().name(), player.getTeam().getName(),
                                player.getTeam().getStadiumName())));


        return stringBuilder.toString();
    }
}
