package com.square_games.demo;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomHeartbeat {

    private final Random random = new Random();

    public int get() {
        return random.nextInt(101);
    }
}
