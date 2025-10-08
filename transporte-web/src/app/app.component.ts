import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'transporte-web';

  constructor(private authService: AuthService, private router: Router){}

  ngOnInit() {
  const token = this.authService.getToken();

  if (!token || this.authService.isTokenExpired(token)) {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
}
