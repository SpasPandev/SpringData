package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT p FROM Player AS p " +
            "JOIN FETCH p.stat As s " +
            "WHERE p.birthDate > ?1 AND p.birthDate < ?2 " +
            "ORDER BY s.shooting DESC, s.passing DESC, s.endurance desc, p.lastName")
    List<Player> findAllPlayersOrderByShootingDescPassingDescEnduranceDescAndPlayerLastNameBornBetween(LocalDate after, LocalDate before);
}
