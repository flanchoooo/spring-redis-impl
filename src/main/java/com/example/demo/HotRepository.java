package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HotRepository extends JpaRepository<Hot, Long> {
    List<Hot> findByTagerAndAmount(String tager, Double amount);
}
