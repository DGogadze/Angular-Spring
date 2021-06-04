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

  incorrectCredentialsText = "Incorrect credentials, try again"

  id = -1
  operationCode = -1
  operationMessage = ""
  email = ""
  username = ""
  phone = ""
  position = ""

  login(username: string, password: string) {
    this.data.Username = username
    this.data.Password = password
    const body = JSON.stringify(this.data)
    this.httpClient.post<UserResponse>(this.loginPath, body, this.options).subscribe(response => {
      this.operationCode = response.OperationCode
      this.operationMessage = response.OperationMessage
      if (response.UserInfo === null)
        return
      this.id = response.UserInfo.Id
      this.email = response.UserInfo.Email
      this.username = response.UserInfo.Username
      this.phone = response.UserInfo.Phone
      this.position = response.UserInfo.Position
    })
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

export interface UserResponse{
  OperationCode: number,
  OperationMessage: string,
  UserInfo: UserInfo
}

export interface UserInfo{
  Id: number
  Email: string
  Username: string
  Phone: string
  Position: string
}
