import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RockPaperScissorsGameComponent} from "./components/rock-paper-scissors-game/rock-paper-scissors-game.component";
import {PlayerRankingComponent} from "./components/player-ranking/player-ranking.component";
import {GameRulesComponent} from "./components/game-rules/game-rules.component";

const routes: Routes = [
  { path: '', redirectTo: 'game', pathMatch: 'full'},
  { path: 'game', component: RockPaperScissorsGameComponent },
  { path: 'ranking', component: PlayerRankingComponent },
  { path: 'rules', component: GameRulesComponent },
  { path: '**', component: RockPaperScissorsGameComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
