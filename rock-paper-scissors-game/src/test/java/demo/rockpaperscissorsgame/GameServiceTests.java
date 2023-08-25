package demo.rockpaperscissorsgame;

import demo.rockpaperscissorsgame.models.Move;
import demo.rockpaperscissorsgame.models.Result;
import demo.rockpaperscissorsgame.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {


    @InjectMocks
    GameService gameService;

    @Test
    void shouldReturnPlayerAsWinner() {

        Move playerMove1 = Move.PAPER;
        Move houseMove1 = Move.ROCK;
        Result result =gameService.checkMatchResult(playerMove1, houseMove1);

        assertNotNull(result);
        assertSame(result, Result.WIN);

        Move playerMove2 = Move.SCISSORS;
        Move houseMove2 = Move.PAPER;
        Result result2 =gameService.checkMatchResult(playerMove2, houseMove2);

        assertNotNull(result);
        assertSame(result2, Result.WIN);

        Move playerMove3 = Move.ROCK;
        Move houseMove3 = Move.SCISSORS;
        Result result3 =gameService.checkMatchResult(playerMove3, houseMove3);

        assertNotNull(result3);
        assertSame(result3, Result.WIN);
    }

    @Test
    void shouldReturnPlayerLost() {

        Move playerMove1 = Move.PAPER;
        Move houseMove1 = Move.SCISSORS;
        Result result =gameService.checkMatchResult(playerMove1, houseMove1);

        assertNotNull(result);
        assertSame(result, Result.LOSS);

        Move playerMove2 = Move.SCISSORS;
        Move houseMove2 = Move.ROCK;
        Result result2 =gameService.checkMatchResult(playerMove2, houseMove2);

        assertNotNull(result);
        assertSame(result2, Result.LOSS);

        Move playerMove3 = Move.ROCK;
        Move houseMove3 = Move.PAPER;
        Result result3 =gameService.checkMatchResult(playerMove3, houseMove3);

        assertNotNull(result3);
        assertSame(result3, Result.LOSS);
    }

    @Test
    void shouldReturnMatchAsTie() {

        Move playerMove1 = Move.PAPER;
        Move houseMove1 = Move.PAPER;
        Result result =gameService.checkMatchResult(playerMove1, houseMove1);

        assertNotNull(result);
        assertSame(result, Result.TIE);

        Move playerMove2 = Move.SCISSORS;
        Move houseMove2 = Move.SCISSORS;
        Result result2 =gameService.checkMatchResult(playerMove2, houseMove2);

        assertNotNull(result);
        assertSame(result2, Result.TIE);

        Move playerMove3 = Move.ROCK;
        Move houseMove3 = Move.ROCK;
        Result result3 =gameService.checkMatchResult(playerMove3, houseMove3);

        assertNotNull(result3);
        assertSame(result3, Result.TIE);
    }
}
