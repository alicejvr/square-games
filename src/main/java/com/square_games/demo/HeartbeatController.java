package com.square_games.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.random.RandomGenerator;

@RestController
public class HeartbeatController {

    @Autowired
    private RandomHeartbeat randomHeartbeat;

    @GetMapping("/heartbeat")
    public int heartbeat() {
        return randomHeartbeat.get();
    }

    @GetMapping("/heartbeat2")
    public int heartbeat() {
        return randomHeartbeat.get();
    }
}
