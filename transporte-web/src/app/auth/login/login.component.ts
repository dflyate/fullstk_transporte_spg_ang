import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import {jwtDecode} from 'jwt-decode';
import { DialogService } from 'src/app/shared/services/dialog.service';
import { ErrorMessages } from 'src/app/core/constants/messages.constants';
import { Roles } from 'src/app/core/constants/roles.contant';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginForm!: FormGroup; 
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private dialogService: DialogService,
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onLogin(): void {
    if (this.loginForm.invalid) return;

    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: (response) => {
        const token = response.token;
        localStorage.setItem('token', token);

        try {
          const decoded: any = jwtDecode(token);
          const role = decoded?.role;

          localStorage.setItem('user', JSON.stringify(decoded));

          if (role === Roles.ADMIN) {
            this.router.navigate(['/admin']);
          } else if (role === Roles.EMPRESA) {
            this.router.navigate(['/company']);
          } else {
            this.errorMessage = 'Rol no reconocido';
          }

        } catch (e) {
          this.errorMessage = 'Token inválido';
        }
      },
      error: (err) => {
        let errorMessage = err?.error?.message ? err.error.message : ErrorMessages.INTERNAL_SERVER_ERROR;
        
        this.dialogService.openDialog('error', 'Error de inicio de sesión', errorMessage);

      }
    });
  }

  getControl(name: string): FormControl {
    return this.loginForm.get(name) as FormControl;
  }
}
