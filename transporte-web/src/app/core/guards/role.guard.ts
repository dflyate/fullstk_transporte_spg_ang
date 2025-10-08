import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  Router,
  UrlTree
} from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service'; 

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree {
    const expectedRoles: string[] = route.data['roles'];
    const user = this.authService.getUser();

    if (user && expectedRoles.includes(user.role)) {
      return true;
    }

    // Redirige si no tiene el rol adecuado
    return this.router.createUrlTree(['/auth/login']);
  }
}
