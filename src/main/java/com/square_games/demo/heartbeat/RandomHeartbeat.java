package com.square_games.demo.heartbeat;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class RandomHeartbeat {

    List<Integer> history = new ArrayList<Integer>();

    private final Random random = new Random();

    public int get() {
        int randomNb = random.nextInt(101); // génère un nb random
        history.add(randomNb); // l'ajoute à la liste d'int history déclaré ligne 12 (insertion du heartbeaten "bdd")
        return randomNb;
    }

    public String msgId(String id) {
        return "Vous avez demandé l'id " + id;
    }

    public String msgIds(String id1, String id2, String id3) {
        return "Vous avez demandé les ID suivants : " + id1 +", "+ id2 +", et "+ id3;
    }

    public String gethistory() { // fonction qui renvoie la liste history
        return history.toString(); //  sous forme de string (liste de ts les heartbeat en "bdd")
    }
}
