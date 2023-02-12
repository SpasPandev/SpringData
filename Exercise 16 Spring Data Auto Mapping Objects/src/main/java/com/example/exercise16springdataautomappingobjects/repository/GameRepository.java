package com.example.exercise16springdataautomappingobjects.repository;

import com.example.exercise16springdataautomappingobjects.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByTitle(String title);
}
