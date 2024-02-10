import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountingAccountsListComponent } from './accounting-accounts-list.component';

describe('AccountingAccountsListComponent', () => {
  let component: AccountingAccountsListComponent;
  let fixture: ComponentFixture<AccountingAccountsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountingAccountsListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccountingAccountsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
