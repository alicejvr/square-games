package com.square_games.demo.heartbeat;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomHeartbeat {

    private final Random random = new Random();

    public int get() {
        return random.nextInt(101);
    }

    public String msgId(String id) {
        return "Vous avez demandé l'id " + id;
    }

    public String msgIds(String id1, String id2, String id3) {
        return "Vous avez demandé les ID suivants : " + id1 +", "+ id2 +", et "+ id3;
    }

}
