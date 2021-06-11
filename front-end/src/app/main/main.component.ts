import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

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

  login(username: string, password: string) {
    console.log(this.httpClient.get<any>("https://localhost:8080/api/user/get").subscribe(response => {response}))
  }

  registration(username: string, password: string, repeatPassword: string, email: string) {

  }
}
