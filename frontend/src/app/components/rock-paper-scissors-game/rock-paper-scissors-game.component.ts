import {Component} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from "@angular/material/dialog";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {PlayerService} from "../../services/player.service";
import {Player} from "../../models/player";
import {GameService} from "../../services/game.service";
import {Move} from "../../models/move";
import {Result} from "../../models/result";

@Component({
  selector: 'app-rock-paper-scissors-game',
  templateUrl: './rock-paper-scissors-game.component.html',
  styleUrls: ['./rock-paper-scissors-game.component.scss']
})
export class RockPaperScissorsGameComponent {

  protected readonly Result = Result;

  moves = [
    {index: 0, move: Move.PAPER, name: "paperImg", imgUrl: "assets/moves/paper.png"},
    {index: 1, move: Move.ROCK, name: "rockImg", imgUrl: "assets/moves/rock.png"},
    {index: 2, move: Move.SCISSORS, name: "scissorsImg", imgUrl: "assets/moves/scissors.png"}
  ]
  paperImg = "assets/moves/paper.png";
  rockImg = "assets/moves/rock.png";
  scissorsImg = "assets/moves/scissors.png";

  currentMoveImg = "";
  currentMoveTabIndex = 0;
  currentHouseMoveImg = "";

  playerScore = 0;
  houseScore = 0;
  matchResult = Result.WIN;

  loading = true;
  rankingMode = false;

  durationInSeconds = 5;

  constructor(private gameService: GameService,
              private playerService: PlayerService,
              private dialog: MatDialog,
              private _snackBar: MatSnackBar) {
  }

  toggleChanges(event: any) {
    if (event) {
      this.rankingMode = event.checked;
    }
  }

  tabOnClick(index: number) {
    if (this.currentMoveTabIndex === index) {
      this.playerPickMove({index: index});
    }
  }

  playerPickMove(event: any) {
    if (event && event.index !== null) {
      this.loading = true;
      this.currentMoveTabIndex = event.index;
      const move = this.moves.find(m => m.index === event.index);
      if (move) {
        this.currentMoveImg = move.imgUrl;
        this.updateResult();
      }
    }
  }

  updateHouseMove(houseMove: Move) {
    const match = this.moves.find(m => m.move === houseMove);
    if (match) {
      this.currentHouseMoveImg = match.imgUrl;
    }
  }

  updateResult() {
    setTimeout( () => {
      this.checkResult();
    }, 500)
  }

  checkResult () {
    const currentMove = this.moves.find(m => m.index === this.currentMoveTabIndex);
    if (currentMove) {
      this.gameService.calculateHouseMoveAndMatchResult(currentMove.move).subscribe({
        next: matchRes => {
          if (matchRes) {
            this.updateHouseMove(matchRes.houseMove);
            this.matchResult = matchRes.result;
            if (this.matchResult === Result.WIN) {
              this.updateScores(1,0);
            } else if (this.matchResult === Result.LOSS) {
              this.updateScores(0,1);
            }
            this.loading = false;
            if (this.rankingMode && this.matchResult === Result.LOSS
              && this.houseScore === 1 && this.playerScore > 0) {
              this.openDialog();
            }
          }
        }, error: error => console.log(error)
      })
    }
  }

  updateScores(playerScore: number, houseScore: number) {
    this.playerScore +=playerScore;
    this.houseScore +=houseScore;
  }

  resetScores() {
    this.playerScore = 0;
    this.houseScore = 0;
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(PopUpComponent, {
      width: '30vw',
      data: {playerName: ''}
    });

    dialogRef.afterClosed().subscribe(playerName => {
      if (playerName) {
        this.savePlayer(playerName);
      }
      this.resetScores();
    });
  }

  openSnackBar(message: string) {
    this._snackBar.open(message ? message : '', '', {
      duration: this.durationInSeconds * 700,
    });
  }

  savePlayer(playerName: string) {
    const player = new Player(playerName, this.playerScore)
    this.playerService.save(player).subscribe({
      next: p => p ? this.openSnackBar('Player successfully saved') : '',
      error: e => console.error(e)
    })
  }
}

export interface PlayerNameInputData {
  playerName: string;
}
