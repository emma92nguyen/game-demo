import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {MatchResult} from "../models/match-result";
import {Move} from "../models/move";

const baseUrl = 'http://localhost:8080/api/game'

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private http: HttpClient) {
  }

  calculateHouseMoveAndMatchResult(playerMove: Move): Observable<MatchResult> {
    return this.http.get<MatchResult>(`${baseUrl}` + "/playerMove/" + `${playerMove}`);
  }
}
