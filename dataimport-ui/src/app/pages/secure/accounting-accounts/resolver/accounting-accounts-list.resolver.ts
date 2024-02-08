import { BaseListResolver } from "@app/core/resolver/base-list.resolver";
import { AccountingAccounts } from "../service/accounting-accounts.model";
import { Injectable } from "@angular/core";
import { AccountingAccountsService } from "../service/accounting-accounts.service";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AccountingAccountsListResolver extends BaseListResolver<AccountingAccounts> {

    constructor(
        resourceService: AccountingAccountsService,
        router: Router
    ) {
        super(router, resourceService)
    }
    
}