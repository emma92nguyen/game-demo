import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Player} from "../models/player";

const baseUrl = 'http://localhost:8080/api/players'

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  constructor(private http: HttpClient) {
  }

  getTop10Players(): Observable<Player[]> {
    return this.http.get<Player[]>(baseUrl);
  }

  save(player: Player): Observable<any> {
    return this.http.post(baseUrl, player);
  }

}
