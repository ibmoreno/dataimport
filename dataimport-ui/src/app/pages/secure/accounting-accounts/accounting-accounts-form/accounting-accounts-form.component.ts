import { CommonModule } from '@angular/common';
import { Component, Injector } from '@angular/core';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';
import { BaseResourceFormComponent } from '@app/core/component/base-resource-form/base-resource-form.components';
import { FormFieldErrorComponent } from '@app/core/component/form-field-error/form-field-error.component';
import { CovalentDialogsModule } from '@covalent/core/dialogs';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { NgxMaskDirective } from 'ngx-mask';
import { AccountingAccounts } from '../service/accounting-accounts.model';
import { AccountingAccountsService } from '../service/accounting-accounts.service';
import { NoBlank } from '@app/core/validator/CustomValidators';

@Component({
  selector: 'app-accounting-accounts-form',
  standalone: true,
  imports: [
    CommonModule,
    MatDividerModule,
    MatCardModule,
    MatIconModule,
    MatTooltipModule,
    MatButtonModule,
    MatInputModule,
    RouterModule,
    MatSelectModule,
    CovalentDialogsModule,
    CovalentLayoutModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    FormFieldErrorComponent,
    NgxMaskDirective],
  templateUrl: './accounting-accounts-form.component.html',
  styleUrl: './accounting-accounts-form.component.scss'
})
export class AccountingAccountsFormComponent extends BaseResourceFormComponent<AccountingAccounts> {


  constructor(
    protected injector: Injector,
    protected accountingAccountsService: AccountingAccountsService,
  ) {
    super(injector, AccountingAccounts.getInstace(), accountingAccountsService, AccountingAccounts.fromJson);
  }

  protected override buildResourceForm(): FormGroup<any> {
    return this.fb.group({
      id: [],
      description: [, { validators: [Validators.required, NoBlank], updateOn: 'change' }],
      aggregateAccountId: [],
      active: [ { validators: [Validators.required], updateOn: 'change' }],
      createdAt: [],
      updatedAt: [],
    })
  }
  
  protected override onAfterUpdate(resource: AccountingAccounts): void {
    if (this.actionSystem.isAlter()) {
      this.router.navigateByUrl(`/accounting-accounts/${resource.id}/view`);
    } else {
      this.router.navigateByUrl('/accounting-accounts');
    }
  }

}
