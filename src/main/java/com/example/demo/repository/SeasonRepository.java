package com.example.demo.repository;

import com.example.demo.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    Optional<Season> findBySeasonName(String seasonName);
}
