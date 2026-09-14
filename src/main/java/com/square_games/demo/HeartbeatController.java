package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {

    @Autowired
    private RandomHeartbeat randomHeartbeat;

    @GetMapping("/heartbeat")
    public int heartbeat() {
        return randomHeartbeat.get();
    }

    @GetMapping("/heartbeat2")
    public String heartbeat2() {
        return "BPM"+ randomHeartbeat.get();
    }

    @GetMapping("/heartbeat3")
    public String heartbeat3() {
        return "<b>BPM</b>"+ randomHeartbeat.get();
    }

    @GetMapping("/heartbeat/{id}")
    public String getId(@PathVariable String id) {
        System.out.println("ID du heartbeat : " + id);
        return randomHeartbeat.msgId(id);
    }

    @GetMapping("/heartbeats/{id1}/{id2}/{id3}")
    public String getIds(@PathVariable String id1, @PathVariable String id2, @PathVariable String id3) {
        System.out.println("IDs des heartbeats : " + id1 +", "+ id2 +", "+ id3);
        return randomHeartbeat.msgIds(id1, id2, id3);
    }

}
