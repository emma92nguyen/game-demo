package demo.rockpaperscissorsgame;

import demo.rockpaperscissorsgame.controllers.GameController;
import demo.rockpaperscissorsgame.models.Move;
import demo.rockpaperscissorsgame.models.MatchResult;
import demo.rockpaperscissorsgame.models.Result;
import demo.rockpaperscissorsgame.services.GameService;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

@WebMvcTest(GameController.class)
public class GameControllerTests {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHouseMoveAndMatchResult() throws Exception{
        Move playerMove = Move.PAPER;
        MatchResult matchResult = new MatchResult(Move.ROCK, Result.WIN);

        when(gameService.calculateHouseMoveAndMatchResult(any(Move.class))).thenReturn(Optional.of(matchResult));
        mockMvc.perform(get("/api/game/playerMove/" + playerMove))
                .andExpect(status().isOk())
                .andExpect(content().json("{'houseMove':'ROCK', 'result':'WIN'}"))
                .andDo(print());
    }

    @Test
    void shouldReturnBadRequestStatusWithInvalidMove() throws Exception{
        String playerMove = "invalidMove";

        mockMvc.perform(get("/api/game/playerMove/" + playerMove))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
