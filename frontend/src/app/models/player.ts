export class Player {

  rank?: number;
  playerName: string;
  numberOfWinsInARow: number;
  createdTime = new Date();

  constructor(playerName: string, numberOfWinsInARow: number) {
    this.playerName = playerName;
    this.numberOfWinsInARow = numberOfWinsInARow;
  }

}
