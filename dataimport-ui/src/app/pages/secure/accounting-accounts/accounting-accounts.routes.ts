import { Routes } from "@angular/router";
import { AccountingAccountsListComponent } from "./accounting-accounts-list/accounting-accounts-list.component";
import { AccountingAccountsListResolver } from "./resolver/accounting-accounts-list.resolver";
import { ActionSystemModel } from "@app/core/util/action-system.model";
import { AccountingAccountsFormComponent } from "./accounting-accounts-form/accounting-accounts-form.component";
import { BaseFormDeactivateResolve } from "@app/core/resolver/base-form-deactivate.resolve";
import { AccountingAccountsFormResolver } from "./resolver/accounting-accounts-form.resolver";

export const AccountingAccountsRoutes: Routes = [
    {
        path: '',
        component: AccountingAccountsListComponent,
        resolve: { resolverData: AccountingAccountsListResolver },
        runGuardsAndResolvers: 'pathParamsOrQueryParamsChange',
        data: { currentAction: ActionSystemModel.LIST, title: 'CONTAS' }
    },
    {
        path: 'new',
        component: AccountingAccountsFormComponent,
        canDeactivate: [BaseFormDeactivateResolve],
        data: { currentAction: ActionSystemModel.ADD, title: 'CADASTRAR CONTAS' }
    },
    {
        path: ':id/view',
        component: AccountingAccountsFormComponent,
        resolve: { resolverData: AccountingAccountsFormResolver },
        data: { currentAction: ActionSystemModel.VIEW, title: 'DETALHE CONTAS' }
    },
    {
        path: ':id/edit',
        component: AccountingAccountsFormComponent,
        resolve: { resolverData: AccountingAccountsFormResolver },
        canDeactivate: [BaseFormDeactivateResolve],
        data: { currentAction: ActionSystemModel.ALTER, title: 'EDITAR CONTAS' }
    },
    
]