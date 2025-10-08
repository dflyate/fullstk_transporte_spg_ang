import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { UserContextService } from '../services/user-context.service';
import { AuthService } from 'src/app/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService,
    private userContext: UserContextService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> {
    if (!this.authService.isAuthenticated()) {
      return of(this.router.createUrlTree(['/auth/login']));
    }

    const roles = this.authService.getUserRole();

    if (this.userContext.hasTransportCompanyId() || roles.includes('ADMIN')) {
      return of(true);
    }

    return this.authService.getUserContext().pipe(
      map((data) => {
        this.userContext.setTransportCompanyId(data.transportCompanyId);
        return true;
      }),
      catchError(() => {
        return of(this.router.createUrlTree(['/auth/login']));
      })
    );
  }
}
