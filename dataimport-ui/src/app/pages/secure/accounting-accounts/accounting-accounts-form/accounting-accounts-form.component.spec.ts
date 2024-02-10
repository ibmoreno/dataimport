import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountingAccountsFormComponent } from './accounting-accounts-form.component';

describe('AccountingAccountsFormComponent', () => {
  let component: AccountingAccountsFormComponent;
  let fixture: ComponentFixture<AccountingAccountsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountingAccountsFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AccountingAccountsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
