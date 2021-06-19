import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CookieService} from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  // @ts-ignore
  authenticationRequest: AuthenticationRequest

  apiUrl = environment.api_url

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService
  ) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json', 'Access-Control-Allow-Origin':'*',
      "Authorization" : this.cookieService.get("Token")
    })
  }

  validate(){
    this.authenticationRequest.Username = this.cookieService.get("Username")
    let requestBodyString = JSON.stringify(this.authenticationRequest)
    this.httpClient.post<AuthenticationResponse>(this.apiUrl + "auth",requestBodyString,this.httpOptions).subscribe(response => {
      return response.isAuthenticated
    })
  }

  ngOnInit(): void {
  }

}

export interface AuthenticationRequest{
  Username: string,
  Token: String
}

export interface AuthenticationResponse{
  isAuthenticated: boolean
}
