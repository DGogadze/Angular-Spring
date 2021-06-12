import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  ngOnInit(): void {
  }

  httpOptions = {
    headers: new HttpHeaders({
  'Content-Type':  'application/json', 'Access-Control-Allow-Origin':'*'
    })
  }

  registrationResponse: RegistrationResponse = {OperationCode: -1,OperationMessage: ""}
  registrationRequest: RegistrationRequest = {Email: "",Password: "",Phone: "",Position: "",Username: ""}

  loginRequest: LoginRequest = {Username: "",Password: ""}
  userInfo: UserInfo = {Id:-1,Email:"",Username:"",Phone:"",Position:""}
  loginResponse: LoginResponse = {OperationCode: 0,OperationMessage: "",UserInfo:this.userInfo}

  login(username: string, password: string) {
    this.loginRequest.Username = username
    this.loginRequest.Password = password
    this.httpClient.post<LoginResponse>(environment.api_url + "user/get",
      JSON.stringify(this.loginRequest),
      this.httpOptions).subscribe(response => {
        this.loginResponse = response
        console.log(this.loginResponse)
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
      this.registrationResponse.OperationCode = response.OperationCode
      this.registrationResponse.OperationMessage = response.OperationMessage
      console.log(this.registrationResponse)
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
