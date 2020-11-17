package com.example.demo.repository;

import com.example.demo.model.DeskClerk;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeskClerkRepository extends JpaRepository<DeskClerk, Long> {
    Optional<DeskClerk> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
