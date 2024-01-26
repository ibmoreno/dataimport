import { Routes } from '@angular/router';
import { CustomersListComponent } from './customers-list/customers-list.component';
import { CustomersListResolver } from './resolver/customers-list.resolver';
import { ActionSystemModel } from '@app/core/util/action-system.model';
import { CustomersImportComponent } from './customers-import/customers-import.component';
import { CustomersFormResolver } from './resolver/customers-form.resolver';
import { CustomersFormComponent } from './customers-form/customers-form.component';
import { BaseFormDeactivateResolve } from '@app/core/resolver/base-form-deactivate.resolve';

export const CustomersRoutes: Routes = [
    {
        path: '',
        component: CustomersListComponent,
        resolve: { resolverData: CustomersListResolver },
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
        data: { currentAction: ActionSystemModel.LIST, title: 'CLIENTES' }
    },
    {
        path: 'new',
        component: CustomersFormComponent,
        canDeactivate: [BaseFormDeactivateResolve],
        data: { currentAction: ActionSystemModel.ADD, title: 'CADASTRAR CLIENTE' }
    },
    {
        path: ':id/view',
        component: CustomersFormComponent,
        resolve: { resolverData: CustomersFormResolver },
        data: { currentAction: ActionSystemModel.VIEW, title: 'DETALHE CLIENTE' }
    },
    {
        path: ':id/edit',
        component: CustomersFormComponent,
        resolve: { resolverData: CustomersFormResolver },
        canDeactivate: [BaseFormDeactivateResolve],
        data: { currentAction: ActionSystemModel.ALTER, title: 'EDITAR CLIENTE' }
    },
    {
        path: ':id/import',
        component: CustomersImportComponent,
        resolve: { resolverData: CustomersFormResolver },
    },
];