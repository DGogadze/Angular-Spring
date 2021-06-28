import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticationResponse, AuthenticationService} from "../services/authentication.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  authenticated = false

  constructor(
    private httpClient: HttpClient,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.authenticationService.validate().subscribe((response : AuthenticationResponse) => {
      this.authenticated = response.Authenticated
    })
  }

}
