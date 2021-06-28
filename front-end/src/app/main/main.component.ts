import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {CookieService} from "ngx-cookie-service";
import {AuthenticationResponse, AuthenticationService} from "../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  // @ts-ignore
  loginResponse : LoginResponse;
  // @ts-ignore
  registrationResponse : RegistrationResponse;

  authenticated = false

  constructor(
    private httpClient: HttpClient,
    private cookieService: CookieService,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.authenticationService.validate().subscribe((response : AuthenticationResponse) => {
      this.authenticated = response.Authenticated
    })
  }

  httpOptions = {
    headers: new HttpHeaders({
  'Content-Type':  'application/json', 'Access-Control-Allow-Origin':'*'
    })
  }

  registrationRequest: RegistrationRequest = {Email: "",Password: "",Phone: "",Position: "",Username: ""}
  loginRequest: LoginRequest = {Username: "",Password: ""}

  login(username: string, password: string) {
    this.loginRequest.Username = username
    this.loginRequest.Password = password
    this.httpClient.post<LoginResponse>(environment.api_url + "login",
      JSON.stringify(this.loginRequest),
      this.httpOptions).subscribe(response => {
        this.loginResponse = response
        this.cookieService.set("Token","Bearer " + this.loginResponse.Token)
        this.cookieService.set("Username",username)

      if (this.loginResponse.OperationCode === 0){
        this.router.navigateByUrl("/profile")
        this.authenticated = true
      }
      }
    )
  }

  registration(username: string, password: string, repeatPassword: string, email: string) {
    this.registrationRequest.Username = username
    this.registrationRequest.Password = password
    this.registrationRequest.Email = email
    this.httpClient.post<RegistrationResponse>(environment.api_url + "user/registration",
      JSON.stringify(this.registrationRequest),
      this.httpOptions).
    subscribe(response => {
      this.registrationResponse = response as RegistrationResponse;
      if (this.registrationResponse.OperationCode === 0){
        this.router.navigateByUrl("/profile")
      }
    })
  }
}

export interface RegistrationResponse {
  OperationCode: number,
  OperationMessage: string
}

export interface RegistrationRequest {
  Email: string
  Username: string
  Password: string
  Position: string
  Phone: string
}

export interface LoginResponse {
  OperationCode: number,
  OperationMessage: string,
  Token: string,
  UserInfo: UserInfo
}

export interface LoginRequest {
  Username: string,
  Password: string
}

export interface UserInfo {
  Id: number
  Position: string
  Phone: string
  Email: string
  Username: string
}
