import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  authenticationRequest:AuthenticationRequest = new class implements AuthenticationRequest {
    Username = "";
  }

  apiUrl = environment.api_url

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*',
      "Authorization": this.cookieService.get('Token')
    })
  }

  validate(): Observable<AuthenticationResponse> {
    this.authenticationRequest.Username = this.cookieService.get('Username')
    let requestBodyString = JSON.stringify(this.authenticationRequest)
    return this.httpClient.post<AuthenticationResponse>(this.apiUrl + "auth", requestBodyString, this.httpOptions)
  }
}

export interface AuthenticationRequest {
  Username: string
}

export interface AuthenticationResponse {
  Authenticated: boolean
}
