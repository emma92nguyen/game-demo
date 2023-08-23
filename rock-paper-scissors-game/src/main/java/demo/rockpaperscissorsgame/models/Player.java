package demo.rockpaperscissorsgame.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String playerName;

    private int numberOfWinsInARow;

    private LocalDateTime createdTime;

    public Player() {}

    public Player(String playerName, int numberOfWinsInARow, LocalDateTime createdTime) {
        this.playerName = playerName;
        this.numberOfWinsInARow = numberOfWinsInARow;
        this.createdTime = createdTime;
    }

}
