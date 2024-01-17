import { Routes } from '@angular/router';
import { CustomersListComponent } from './customers-list/customers-list.component';
import { CustomersListResolver } from './resolver/customers-list.resolver';
import { ActionSystemModel } from '@app/core/util/action-system.model';
import { CustomersImportComponent } from './customers-import/customers-import.component';
import { CustomersFormResolver } from './resolver/customers-form.resolver';

export const CustomersRoutes: Routes = [
    { 
        path: '', 
        component: CustomersListComponent,
        resolve: { resolverData: CustomersListResolver },
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
        data: { currentAction: ActionSystemModel.LIST, title: 'CLIENTES' }
    },
    { 
        path: ':id/import', 
        component: CustomersImportComponent,
        resolve: { resolverData: CustomersFormResolver },
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
    },    
];