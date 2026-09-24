package com.square_games.demo.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class GameEntity {

    @Id
    public String id;

    public String factoryId;
    public int boardSize;

    @ElementCollection
    public Set<UUID> playerIds;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    public List<GameTokenEntity> tokens;
}