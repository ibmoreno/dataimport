import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { RouterModule } from '@angular/router';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentFileModule } from '@covalent/core/file';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-customers-import',
  standalone: true,
  imports: [
    CommonModule, 
    CovalentFileModule,
    CovalentLayoutModule, 
    RouterModule,
    MatIconModule,
    MatCardModule, 
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule, 
    MatDividerModule, MatButtonModule],
  templateUrl: './customers-import.component.html',
  styleUrl: './customers-import.component.scss'
})
export class CustomersImportComponent {

  protected submittingForm: boolean = false;
  protected file: File | any;

  onFileDropped(files: FileList | File) {
    console.log(">>>>>>>>>>>>>>>>>>>>>>>>");
    if (files instanceof File) {
      this.file = files;
      console.log(this.file);
    }
  }

}
