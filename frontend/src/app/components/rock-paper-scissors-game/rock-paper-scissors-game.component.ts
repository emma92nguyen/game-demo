import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import {MatDialog} from "@angular/material/dialog";
import {PopUpComponent} from "../pop-up/pop-up.component";
import {PlayerService} from "../../services/player.service";
import {Player} from "../../models/player";

@Component({
  selector: 'app-rock-paper-scissors-game',
  templateUrl: './rock-paper-scissors-game.component.html',
  styleUrls: ['./rock-paper-scissors-game.component.scss']
})
export class RockPaperScissorsGameComponent {

  hands = [
    {id: 0, name: "paperImg", imgUrl: "assets/hands/paper.png"},
    {id: 1, name: "rockImg", imgUrl: "assets/hands/rock.png"},
    {id: 2, name: "scissorsImg", imgUrl: "assets/hands/scissors.png"}
  ]
  paperImg = "assets/hands/paper.png";
  rockImg = "assets/hands/rock.png";
  scissorsImg = "assets/hands/scissors.png";

  currentHand = "";
  currentHandTabIndex = 0;
  currentHouseHandIndex = 0;
  currentHouseHand = "";

  playerScore = 0;
  houseScore = 0;
  matchResult = 0;

  loading = true;
  rankingMode = false;

  durationInSeconds = 5;

  constructor(private playerService: PlayerService, private dialog: MatDialog, private _snackBar: MatSnackBar) {
  }

  toggleChanges(event: any) {
    if (event) {
      this.rankingMode = event.checked;
      console.log(this.rankingMode);
    }
  }

  tabOnClick(index: number) {
    if (this.currentHandTabIndex === index) {
      this.playerPickHand({index: index});
    }
  }

  playerPickHand(event: any) {
    console.log(event);
    if (event && event.index !== null) {
      this.loading = true;
      this.currentHandTabIndex = event.index;
      const hand = this.hands.find(h => h.id === event.index);
      if (hand) {
        this.currentHand = hand.imgUrl;
        this.updateResult();
      }
    }
  }

  calculateHouseMove() {
    this.currentHouseHandIndex = Math.floor(Math.random() * 3);
    const match = this.hands.find(h => h.id === this.currentHouseHandIndex);
    if (match) {
      this.currentHouseHand = match.imgUrl;
    }
  }

  updateResult() {
    setTimeout( () => {
      this.calculateHouseMove();
      this.loading = false;
      this.checkResult();
      if (this.rankingMode && this.matchResult === -1 && this.houseScore === 1 && this.playerScore > 0) {
        this.openDialog();
      }
    }, 500)
  }

  checkResult () {
    console.log(this.matchResult);
    console.log(this.currentHandTabIndex+''+this.currentHouseHandIndex);

    if (this.currentHandTabIndex === this.currentHouseHandIndex) {
      this.matchResult = 0;
    } else {
      switch (this.currentHandTabIndex + '' + this.currentHouseHandIndex) {
        case ('01'):
        case ('12'):
        case ('20'):
          this.matchResult = 1;
          this.updateScores(1,0);
          break;
        case ('02'):
        case ('10'):
        case ('21'):
          this.matchResult = -1;
          this.updateScores(0,1);
          break;
        default:
          break;
      }
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
      console.log('The dialog was closed');
      console.log(playerName);
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
