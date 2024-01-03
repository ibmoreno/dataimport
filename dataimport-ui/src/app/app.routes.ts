import { Routes } from '@angular/router';

export const AppRoutes: Routes = [
    { path: '', redirectTo: 'customers', pathMatch: 'full' },
    {
        path: 'customers',
        loadChildren: () => import('./pages/secure/customers/customers.routes').then(r => r.CustomersRoutes)
    }
];
