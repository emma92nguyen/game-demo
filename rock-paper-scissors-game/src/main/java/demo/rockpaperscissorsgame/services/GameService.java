package demo.rockpaperscissorsgame.services;

import demo.rockpaperscissorsgame.models.Move;
import demo.rockpaperscissorsgame.models.MatchResult;
import demo.rockpaperscissorsgame.models.Result;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class GameService {

    public Optional<MatchResult> calculateHouseMoveAndMatchResult (Move playerMove) {
        Move houseMove = calculateHouseMove();
        return Optional.of(new MatchResult(houseMove,
                checkMatchResult(playerMove, houseMove)));
    }

    private Move calculateHouseMove() {
        return Move.values()[new Random().nextInt(3)];
    }

    private Result checkMatchResult(Move playerMove, Move houseMove) {
        if (playerMove == houseMove) {
            return Result.TIE;
        }
        if ((playerMove == Move.ROCK && houseMove == Move.PAPER)
        || (playerMove == Move.PAPER && houseMove == Move.SCISSORS)
        || (playerMove == Move.SCISSORS && houseMove == Move.ROCK)
        ) {
            return Result.LOSS;
        }
        return Result.WIN;
    }
}
