import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "./core/guards/auth.guard";
import { RoleGuard } from "./core/guards/role.guard";
import { Roles } from "./core/constants/roles.contant";

const routes: Routes = [
    {path: '', redirectTo: 'auth/login',pathMatch:'full'},

    {
        path: 'auth',
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
    },

    {
        path: 'admin',
        loadChildren: () => import('./features/admin/admin.module').then(m => m.AdminModule),
        canActivate: [AuthGuard, RoleGuard],
        data: { roles: [Roles.ADMIN] }
    },

    {
        path: 'company',
        loadChildren: () => import('./features/empresa/empresa.module').then(m => m.EmpresaModule),
        canActivate: [AuthGuard, RoleGuard],
        data: { roles: [Roles.EMPRESA] }
    },

    {
        path: '**', redirectTo: 'auth/login'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule{}