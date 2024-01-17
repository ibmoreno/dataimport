import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from '@app/core/services/base-resource.service';
import { Customers } from './customers.model';
import { Observable, Subscription, finalize, first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomersService extends BaseResourceService<Customers> {



  constructor(protected injector: Injector) {
    super('/api/customers', injector, Customers.fromJson);
   }

   uploadFile(customerId: number, year: number, months: number[], file: File): Observable<any> {
    console.log(file);
    const formData = new FormData(); 
    const url = `${this.apiPath}/${customerId}/import/${year}`
    formData.append('file', file, file?.name);
     return this.http.post(url, formData, { 
      reportProgress: true, 
      observe: 'events', 
      params: {
        months: months
      }
    })
   }

}
