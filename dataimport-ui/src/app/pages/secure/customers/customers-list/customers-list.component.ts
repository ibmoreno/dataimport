import { CommonModule } from '@angular/common';
import { Component, Injector } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { BaseResourceListComponent } from '@app/core/component/base-resource-list.components';
import { PageableModel } from '@app/core/util/pageable.model';
import { CovalentCommonModule } from '@covalent/core/common';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentSearchModule } from '@covalent/core/search';
import { Customers } from '../service/customers.model';
import { CustomersService } from '../service/customers.service';

@Component({
  selector: 'app-customers-list',
  standalone: true,
  imports: [
    CommonModule, 
    CovalentCommonModule,
    CovalentLayoutModule, 
    CovalentSearchModule, 
    MatDividerModule, 
    MatCardModule, 
    MatPaginatorModule, 
    MatButtonModule, 
    MatTableModule, MatIconModule, MatSortModule, RouterModule],
    templateUrl: './customers-list.component.html',
  styleUrl: './customers-list.component.scss'
})
export class CustomersListComponent extends BaseResourceListComponent<Customers> {

  constructor(
    private customersService: CustomersService,
    private injector: Injector) {
    super(injector);
    this.columns = ['id', 'name', 'cnpj', 'createdAt', 'updatedAt', 'import', 'action'];

  }

  protected override search(searchText: string, pageableModel: PageableModel<Customers>): void {
    console.log(searchText)
  }

}
function style(arg0: { opacity: number; }): import("@angular/animations").AnimationStyleMetadata {
  throw new Error('Function not implemented.');
}

