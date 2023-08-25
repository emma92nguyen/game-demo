package demo.rockpaperscissorsgame.controllers;

import demo.rockpaperscissorsgame.models.Move;
import demo.rockpaperscissorsgame.models.MatchResult;
import demo.rockpaperscissorsgame.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/playerMove/{playerMove}")
    public ResponseEntity<MatchResult> calculateHouseMoveAndMatchResult(@PathVariable("playerMove") Move playerMove) {
        return this.gameService.calculateHouseMoveAndMatchResult(playerMove)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}
