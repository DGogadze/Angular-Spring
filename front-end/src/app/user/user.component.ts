import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  headers = new HttpHeaders({
    'Content-Type': 'application/json'
  })
  options = {headers: this.headers}

  private apiUrl = environment.apiUrl
  private loginPath = this.apiUrl + "user/get"

  private data: Login = new Login()

  loginFormDraw = true
  profileDraw = false

  user = new Profile()

  login(username: string, password: string) {
    this.data.Username = username
    this.data.Password = password
    const body = JSON.stringify(this.data)
    this.loginFormDraw = false
    this.profileDraw = true
    this.httpClient.post<any>(this.loginPath, body, this.options).subscribe(response => response)
  }

  ngOnInit(): void {
  }

}

export class Login {
  constructor() {
  }

  Username: string | undefined
  Password: string | undefined
}

export class Profile {
  constructor() {
  }

  operationCode: number = -1
  operationMessage: string = ""
  email: string = ""
  username: string = ""
}
