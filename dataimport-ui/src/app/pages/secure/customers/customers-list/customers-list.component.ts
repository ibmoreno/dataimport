import { CommonModule } from '@angular/common';
import { Component, Injector, TemplateRef, ViewChild } from '@angular/core';
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
import { Customers } from '../service/customers.model';
import { CustomersService } from '../service/customers.service';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-customers-list',
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
  templateUrl: './customers-list.component.html',
  styleUrl: './customers-list.component.scss'
})
export class CustomersListComponent extends BaseResourceListComponent<Customers> {

  private columns: IColumnDefinitions[] = [
    { propertyName: 'id', showHandset: false },
    { propertyName: 'name', showHandset: true },
    { propertyName: 'cnpj', showHandset: false },
    { propertyName: 'import', showHandset: true },
    { propertyName: 'action', showHandset: true },
  ]

  constructor(
    private customersService: CustomersService,
    private injector: Injector) {
    super(injector);
    this.columnDefinitions = this.columns;
  }

  protected override search(searchText: string, pageableModel: PageableModel<Customers>): void {
    this.customersService.search(searchText, pageableModel).subscribe({
      next: (pageableModel: PageableModel<Customers>) => {
        this.pageableModel = pageableModel;
      },
    });
  }

}


