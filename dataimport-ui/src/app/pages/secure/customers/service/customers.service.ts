import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from '@app/core/services/base-resource.service';
import { Customers } from './customers.model';
import { Observable, Subscription, finalize, first } from 'rxjs';
import { PageableModel } from '@app/core/util/pageable.model';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CustomersService extends BaseResourceService<Customers> {

  constructor(protected injector: Injector) {
    super('/api/customers', injector, Customers.fromJson);
   }

   search(searchText: string, page: PageableModel<Customers>): Observable<PageableModel<Customers>> {
      const params: HttpParams = new HttpParams().set('search', searchText);
      return this.page(params, `${this.apiPath}/search`, page);
   }

   uploadFile(customerId: number, year: number, months: number[], file: File): Observable<any> {
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

   deleteMoviments(customerId: number, year: number, months: number[]): Observable<any> {
    const url = `${this.apiPath}/${customerId}/delete/${year}`
    return this.http.delete(url, { 
      params: {
        months: months
      }
    })
   }

}
