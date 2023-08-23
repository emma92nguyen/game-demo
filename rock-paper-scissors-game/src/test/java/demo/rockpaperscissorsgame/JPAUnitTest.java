package demo.rockpaperscissorsgame;

import static org.assertj.core.api.Assertions.assertThat;

import demo.rockpaperscissorsgame.models.Player;
import demo.rockpaperscissorsgame.repositories.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
public class JPAUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PlayerRepository playerRepository;

    @Test
    public void should_return_empty_list_if_repository_is_empty() {
        Iterable<Player> players = playerRepository.findAll();
        assertThat(players).isEmpty();
    }

    @Test
    public void should_return_top_10_players_in_descending_order() {
        List<Player> players = new ArrayList<>(
            Arrays.asList(
                    new Player("Player 1", 5, LocalDateTime.now()),
                    new Player("Player 2", 3, LocalDateTime.now()),
                    new Player("Player 3", 4, LocalDateTime.now()),
                    new Player("Player 4", 2, LocalDateTime.now()),
                    new Player("Player 5", 2, LocalDateTime.now()),
                    new Player("Player 6", 2, LocalDateTime.now()),
                    new Player("Player 7", 1, LocalDateTime.now()),
                    new Player("Player 8", 1, LocalDateTime.now()),
                    new Player("Player 9", 1, LocalDateTime.now()),
                    new Player("Player 10", 1, LocalDateTime.now()),
                    new Player("Player 11", 1, LocalDateTime.now())
            ));
        players.forEach(p -> entityManager.persist(p));

        List<Player> results = playerRepository.findTop10ByOrderByNumberOfWinsInARowDesc();
        assertThat(results).hasSize(10);
        assertThat(results.get(0).getPlayerName()).isEqualTo("Player 1");
        assertThat(results.get(1).getPlayerName()).isEqualTo("Player 3");
        assertThat(results.get(2).getPlayerName()).isEqualTo("Player 2");
    }

    @Test
    public void should_save_a_player() {
        LocalDateTime createdTime = LocalDateTime.now();
        Player savedPlayer = playerRepository.save(new Player("Player 1", 5, createdTime));

        assertThat(savedPlayer).hasFieldOrPropertyWithValue("playerName", "Player 1");
        assertThat(savedPlayer).hasFieldOrPropertyWithValue("numberOfWinsInARow", 5);
        assertThat(savedPlayer).hasFieldOrPropertyWithValue("createdTime", createdTime);
    }
}
