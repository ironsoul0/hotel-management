package com.example.demo.repository;

import com.example.demo.model.Manager;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
