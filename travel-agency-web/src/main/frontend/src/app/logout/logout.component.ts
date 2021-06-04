import { Component, OnInit } from '@angular/core';
import { InfoboxStateEnum } from 'src/models/infobox-state.enum';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {
  InfoboxStateEnum = InfoboxStateEnum;
  login: boolean = false;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.login = this.authService.IsAuthenticated();
  }

  onLogout(){
    this.authService.logout();
    this.login = this.authService.IsAuthenticated();
  }
}
