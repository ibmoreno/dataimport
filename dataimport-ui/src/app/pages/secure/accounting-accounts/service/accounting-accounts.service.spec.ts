import { TestBed } from '@angular/core/testing';

import { AccountingAccountsService } from './accounting-accounts.service';

describe('AccountingAccountsService', () => {
  let service: AccountingAccountsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccountingAccountsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
