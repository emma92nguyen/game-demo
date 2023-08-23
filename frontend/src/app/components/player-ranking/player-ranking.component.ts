import {Component, OnInit} from '@angular/core';
import {Player} from "../../models/player";
import {PlayerService} from "../../services/player.service";

@Component({
  selector: 'app-player-ranking',
  templateUrl: './player-ranking.component.html',
  styleUrls: ['./player-ranking.component.scss']
})
export class PlayerRankingComponent implements OnInit{
  displayedColumns: string[] = ['ranking', 'name', 'numberOfWins', 'createdTime'];
  dataSource: Player[] = [];

  constructor(private playerService: PlayerService) {
  }

  ngOnInit(): void {
    this.playerService.getTop10Players().subscribe({
      next: res => {
        const players: Player[] = [];
        for (let i = 0; i < 10; i++) {
          players[i] = {
            rank: i+1,
            ...res[i]
          };
        }
        console.log(players);
        this.dataSource = players;
      },
      error: err => console.log(err)
    })
  }
}
