import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from '@app/core/services/base-resource.service';
import { Customers } from './customers.model';

@Injectable({
  providedIn: 'root'
})
export class CustomersService extends BaseResourceService<Customers> {

  constructor(protected injector: Injector) {
    super('/api/customers', injector, Customers.fromJson);

   }

}
