import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatPaginatorModule } from '@angular/material/paginator';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentSearchModule } from '@covalent/core/search';

@Component({
  selector: 'app-customers-list',
  standalone: true,
  imports: [CommonModule, CovalentLayoutModule, CovalentSearchModule, MatDividerModule, MatCardModule, MatPaginatorModule, MatButtonModule],
  templateUrl: './customers-list.component.html',
  styleUrl: './customers-list.component.scss'
})
export class CustomersListComponent {

}
