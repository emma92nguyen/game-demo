package demo.rockpaperscissorsgame.services;

import demo.rockpaperscissorsgame.models.Player;
import demo.rockpaperscissorsgame.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> getTop10Players() {
       // return playerRepository.findAll(Sort.by("numberOfWinsInARow").descending());
        return playerRepository.findTop10ByOrderByNumberOfWinsInARowDesc();
    }

    public Optional<Player> createPlayer(Player player) {
        return Optional.of(playerRepository.save(player));
    }
}
