import { Routes } from '@angular/router';
import { TemplateComponent } from './pages/layouts/template/template.component';

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
                loadChildren: () => import('./pages/secure/home/home.routes').then(r => r.HomeRoutes)
            },
            {
                path: 'customers',
                loadChildren: () => import('./pages/secure/customers/customers.routes').then(r => r.CustomersRoutes)
            },
            {
                path: 'accounting-accounts',
                loadChildren: () => import('./pages/secure/accounting-accounts/accounting-accounts.routes').then(r => r.AccountingAccountsRoutes)
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'home'
    }
];
