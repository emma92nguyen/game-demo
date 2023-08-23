package demo.rockpaperscissorsgame;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.rockpaperscissorsgame.controllers.PlayerController;
import demo.rockpaperscissorsgame.models.Player;
import demo.rockpaperscissorsgame.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTests {

    @MockBean
    private PlayerService playerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnListOfTop10PlayersInDescendingOrder() throws Exception {
        List<Player> players = new ArrayList<>(
                Arrays.asList(
                        new Player("Player 1", 5, LocalDateTime.now()),
                        new Player("Player 2", 3, LocalDateTime.now()),
                        new Player("Player 3", 4, LocalDateTime.now()),
                        new Player("Player 4", 4, LocalDateTime.now()),
                        new Player("Player 5", 4, LocalDateTime.now()),
                        new Player("Player 6", 3, LocalDateTime.now()),
                        new Player("Player 7", 3, LocalDateTime.now()),
                        new Player("Player 8", 2, LocalDateTime.now()),
                        new Player("Player 9", 2, LocalDateTime.now()),
                        new Player("Player 10", 1, LocalDateTime.now()),
                        new Player("Player 11", 1, LocalDateTime.now())

                )
        );

        when(playerService.getTop10Players()).thenReturn(players.subList(0,10));
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(10))
                .andDo(print());
    }

    @Test
    void shouldCreatePlayer() throws Exception {
        Player player = new Player("Player 1", 5, LocalDateTime.now());

        when(playerService.createPlayer(any(Player.class))).thenReturn(Optional.of(player));

        mockMvc.perform(post("/api/players").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
