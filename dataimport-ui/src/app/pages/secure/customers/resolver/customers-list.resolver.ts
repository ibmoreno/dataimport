import { Router } from "@angular/router";
import { BaseListResolver } from "@app/core/resolver/base-list.resolver";
import { Customers } from "../service/customers.model";
import { CustomersService } from "../service/customers.service";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class CustomersListResolver extends BaseListResolver<Customers> {

    constructor(
        resourceService: CustomersService,
        router: Router
    ) {
        super(router, resourceService)
    }

}