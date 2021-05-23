import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ButtonStateEnum} from "../../models/button-state.enum";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  created: boolean = false;
  loginForm: FormGroup;
  submitState: ButtonStateEnum=ButtonStateEnum.init;
  error: any;

  constructor(private authService: AuthService) {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {

  }

  onClick(): void {
    this.submitState = ButtonStateEnum.loading;
    if(this.loginForm.valid) {
      this.error=null;
      this.authService.login({
          nickName: this.loginForm.controls.username.value,
          password: this.loginForm.controls.password.value,
        },
        () => {
          if(this.authService.IsAuthenticated()) {
            this.submitState = ButtonStateEnum.success;
            this.created = true;
          }
          else{
            this.submitState=ButtonStateEnum.error;
          }
        },
        error => {
          this.submitState = ButtonStateEnum.error;
          this.error = error;
          console.log(error);
        }
      );
    }
    else{
      this.submitState=ButtonStateEnum.error;
    }
  }

}
