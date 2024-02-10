import { CommonModule } from '@angular/common';
import { Component, Injector } from '@angular/core';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
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
import { PageableModel } from '@app/core/util/pageable.model';
import { NoBlank, RequireMatch } from '@app/core/validator/CustomValidators';
import { CovalentDialogsModule } from '@covalent/core/dialogs';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { NgxMaskDirective } from 'ngx-mask';
import { debounceTime, switchMap } from 'rxjs';
import { AccountingAccounts } from '../service/accounting-accounts.model';
import { AccountingAccountsService } from '../service/accounting-accounts.service';
import { AggregatorAccounts } from '../service/aggregator-accounts.model';

@Component({
  selector: 'app-accounting-accounts-form',
  standalone: true,
  imports: [
    CommonModule,
    MatDividerModule,
    MatAutocompleteModule,
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

  aggregateAccountsList:  AggregatorAccounts[] = [];

  constructor(
    protected injector: Injector,
    protected accountingAccountsService: AccountingAccountsService,
  ) {
    super(injector, AccountingAccounts.getInstace(), accountingAccountsService, AccountingAccounts.fromJson);
  }

  protected override buildResourceForm(): FormGroup<any> {
    const form: FormGroup =
      this.fb.group({
        id: [],
        description: [, { validators: [Validators.required, NoBlank], updateOn: 'change' }],
        aggregateAccount: [null, { validators: [RequireMatch], updateOn: 'change' }],
        active: [{ validators: [Validators.required], updateOn: 'change' }],
        createdAt: [],
        updatedAt: [],
      });

    form.get('aggregateAccount')?.valueChanges.pipe(
      debounceTime(200),
      switchMap((value: string) => this.accountingAccountsService.search(value, new PageableModel())))
      .subscribe((pageableModel: PageableModel<AccountingAccounts>) => (
         this.mapToAggregatorAccount(pageableModel.content)
      ));

    return form;

  }

  private mapToAggregatorAccount(accountingAccountsList: AccountingAccounts[]) {
    const id: number = this.resourceForm.get('id')?.value ?? 0;
    this.aggregateAccountsList = accountingAccountsList
      .filter((accountingAccount: AggregatorAccounts) => accountingAccount.id !== id)
      .map((accountingAccount: AccountingAccounts) => AggregatorAccounts.fromJson(accountingAccount))
  }

  protected override onAfterUpdate(resource: AccountingAccounts): void {
    if (this.actionSystem.isAlter()) {
      this.router.navigateByUrl(`/accounting-accounts/${resource.id}/view`);
    } else {
      this.router.navigateByUrl('/accounting-accounts');
    }
  }

  displayFn(aggregateAccount?: AggregatorAccounts): string {
    console.log(aggregateAccount);
    return aggregateAccount && aggregateAccount.description ? aggregateAccount.description : '';
  }

}
