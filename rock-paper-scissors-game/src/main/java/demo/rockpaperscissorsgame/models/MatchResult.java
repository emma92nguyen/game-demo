package demo.rockpaperscissorsgame.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchResult {
    private Move houseMove;
    private Result result;

    public MatchResult() {}

    public MatchResult(Move houseMove, Result result) {
        this.houseMove = houseMove;
        this.result = result;
    }

    @Override
    public boolean equals(Object matchResult) {
        if (matchResult instanceof MatchResult) {
            return houseMove == ((MatchResult) matchResult).houseMove && result == ((MatchResult) matchResult).result;
        }
        return false;
    }
}
