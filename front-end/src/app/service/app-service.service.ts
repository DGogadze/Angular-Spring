import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AppServiceService {

  authenticated = false;

  constructor(
    private http: HttpClient
  ) {
  }

  authenticate(username: string){
    this.http.get("")
  }
}
