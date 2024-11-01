package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hot")
public class HotController {

    @Autowired
    private HotService hotService;

    @GetMapping("/search")
    public List<Hot> searchByTagerAndAmount(@RequestParam String tager, @RequestParam Double amount) {
        return hotService.findByTagerAndAmount(tager, amount);
    }
}
