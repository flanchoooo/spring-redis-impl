package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotService {

    @Autowired
    private HotRepository hotRepository;

    public List<Hot> findByTagerAndAmount(String tager, Double amount) {
        return hotRepository.findByTagerAndAmount(tager, amount);
    }
}
