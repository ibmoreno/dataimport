import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BaseFormResolve } from "@app/core/resolver/base-form.resolve";
import { AccountingAccounts } from "../service/accounting-accounts.model";
import { AccountingAccountsService } from "../service/accounting-accounts.service";

@Injectable({
    providedIn: 'root'
})
export class AccountingAccountsFormResolver extends BaseFormResolve<AccountingAccounts> {

    constructor(
        resourceService: AccountingAccountsService,
        router: Router) {
        super(router, resourceService, AccountingAccounts.fromJson);
    }
    
}