import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from '@app/core/services/base-resource.service';
import { AccountingAccounts } from './accounting-accounts.model';
import { PageableModel } from '@app/core/util/pageable.model';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AccountingAccountsService extends BaseResourceService<AccountingAccounts> {

  constructor(protected injector: Injector) {
    super('/api/accounting-accounts', injector, AccountingAccounts.fromJson);
   }

   search(searchText: string, page: PageableModel<AccountingAccounts>): Observable<PageableModel<AccountingAccounts>> {
     const params: HttpParams = new HttpParams().set('search', searchText);
     return this.page(params, `${this.apiPath}/search`, page);
   }

}
