import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

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

  // @ts-ignore
  validate(): boolean {
    this.authenticationRequest.Username = this.cookieService.get('Username')
    let requestBodyString = JSON.stringify(this.authenticationRequest)
    this.httpClient.post<AuthenticationResponse>(this.apiUrl + "auth", requestBodyString, this.httpOptions).subscribe(response => {
      return response.Authenticated
    })
  }
}

export interface AuthenticationRequest {
  Username: string
}

export interface AuthenticationResponse {
  Authenticated: boolean
}
