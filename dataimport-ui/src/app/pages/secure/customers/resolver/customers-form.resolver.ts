import { BaseFormResolve } from "@app/core/resolver/base-form.resolve";
import { Customers } from "../service/customers.model";
import { Injectable } from "@angular/core";
import { CustomersService } from "../service/customers.service";
import { Router } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class CustomersFormResolver extends BaseFormResolve<Customers> {
    constructor(
        resourceService: CustomersService,
        router: Router) {
        super(router, resourceService, Customers.fromJson);
    }
}