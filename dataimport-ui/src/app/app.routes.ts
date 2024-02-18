import { CanActivateFn, Routes } from '@angular/router';
import { TemplateComponent } from './pages/layouts/template/template.component';
import { AuthGuard } from './core/authentication/auth-guard';
import { inject } from '@angular/core';

const isAuthenticated: CanActivateFn = (route, state) => {
    return inject(AuthGuard).isAccessAllowed(route, state);
  }
  

export const AppRoutes: Routes = [
    {
        path: '',
        component: TemplateComponent,
        children: [
            {
                path: '',
                redirectTo: 'home',
                pathMatch: 'full',

            },
            {
                path: 'home',
                canActivate: [isAuthenticated],
                loadChildren: () => import('./pages/secure/home/home.routes').then(r => r.HomeRoutes)
            },
            {
                path: 'customers',
                canActivate: [isAuthenticated],
                loadChildren: () => import('./pages/secure/customers/customers.routes').then(r => r.CustomersRoutes),
            },
            {
                path: 'accounting-accounts',
                canActivate: [isAuthenticated],
                loadChildren: () => import('./pages/secure/accounting-accounts/accounting-accounts.routes').then(r => r.AccountingAccountsRoutes)
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'home'
    }
];
