import { Routes } from '@angular/router';
import { CustomersListComponent } from './customers-list/customers-list.component';
import { CustomersListResolver } from './resolver/customers-list.resolver';
import { ActionSystemModel } from '@app/core/util/action-system.model';

export const CustomersRoutes: Routes = [
    { 
        path: '', 
        component: CustomersListComponent,
        resolve: { resolverData: CustomersListResolver },
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
        data: { currentAction: ActionSystemModel.LIST, title: 'LISTA DE CLIENTES' }
    },
];