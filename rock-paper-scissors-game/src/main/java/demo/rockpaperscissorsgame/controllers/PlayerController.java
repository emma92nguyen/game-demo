package demo.rockpaperscissorsgame.controllers;

import demo.rockpaperscissorsgame.models.Player;
import demo.rockpaperscissorsgame.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController()
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public List<Player> getTop10Players() {

        return this.playerService.getTop10Players();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        return this.playerService.createPlayer(player)
                .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

}
