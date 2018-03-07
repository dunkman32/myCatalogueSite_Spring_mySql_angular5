import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import {ServerService} from '../server.service';
import {Router} from '@angular/router';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {
  user = {username: 'lamer', password: 'qwerty'};

  constructor(private serverService: ServerService ,
              private router: Router,
              private authService: AuthService) { }

  ngOnInit() {
  }

  onSignin(form: NgForm) {
    const email = form.value.email;
    const password = form.value.password;
    this.user = {username: email, password: password};
    this.serverService.login(this.user).subscribe(
      (response) => {
        this.onLogin();
        console.log(response);
        console.log(this.user);
        this.router.navigate(['/books']);
      },
      (error) => console.log(error)
    );
  }

  onLogin() {
    this.authService.login();
  }

  onLogout() {
    this.authService.logout();
  }
}
