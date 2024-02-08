import { CommonModule } from '@angular/common';
import { Component, Injector } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';
import { BaseResourceListComponent, IColumnDefinitions } from '@app/core/component/base-resource-list/base-resource-list.components';
import { PageableModel } from '@app/core/util/pageable.model';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentSearchModule } from '@covalent/core/search';
import { AccountingAccounts } from '../service/accounting-accounts.model';
import { AccountingAccountsService } from '../service/accounting-accounts.service';

@Component({
  selector: 'app-accounting-accounts-list',
  standalone: true,
  imports: [
    CommonModule,
    CovalentLayoutModule,
    CovalentSearchModule,
    MatDividerModule,
    MatCardModule,
    MatPaginatorModule,
    MatButtonModule,
    MatTooltipModule,
    MatTableModule, MatIconModule, MatSortModule, RouterModule],
  templateUrl: './accounting-accounts-list.component.html',
  styleUrl: './accounting-accounts-list.component.scss'
})
export class AccountingAccountsListComponent extends BaseResourceListComponent<AccountingAccounts> {

  private columns: IColumnDefinitions[] = [
    { propertyName: 'id', showHandset: false },
    { propertyName: 'description', showHandset: true },
    { propertyName: 'active', showHandset: true },
    { propertyName: 'action', showHandset: true },
  ]

  constructor(
    private accountingAccountsService: AccountingAccountsService,
    private injector: Injector) {
    super(injector);
    this.columnDefinitions = this.columns;
  }

  protected override search(searchText: string, pageableModel: PageableModel<AccountingAccounts>): void {
    this.accountingAccountsService.search(searchText, pageableModel).subscribe({
      next: (pageableModel: PageableModel<AccountingAccounts>) => {
        this.pageableModel = pageableModel;
      },
    });

  }

}
