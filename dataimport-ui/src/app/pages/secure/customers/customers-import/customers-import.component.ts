import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentFileModule } from '@covalent/core/file';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Customers } from '../service/customers.model';
import { CustomersService } from '../service/customers.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { HttpEventType } from '@angular/common/http';
import { FormFieldErrorComponent } from "@core/component/form-field-error/form-field-error.component";
import { NoBlank } from '@app/core/validator/CustomValidators';

@Component({
    selector: 'app-customers-import',
    standalone: true,
    templateUrl: './customers-import.component.html',
    styleUrl: './customers-import.component.scss',
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
        ReactiveFormsModule,
        MatProgressBarModule,
        MatDividerModule, 
        MatButtonModule,
        FormFieldErrorComponent
    ]
})
export class CustomersImportComponent {

  protected customers: Customers = new Customers();
  protected submittingForm: boolean = false;
  protected resourceForm: FormGroup;
  protected uploadProgress: number = 0;
  protected file: File | any;
  protected title: string = '';

  constructor(
    protected route: ActivatedRoute,
    protected formBuilder: FormBuilder,
    protected customerService: CustomersService,
  ) {
    this.customers = this.route.snapshot.data['resolverData'];
    this.title = 'IMPORTAR PLANILHAR - ' + this.customers.name;
    this.resourceForm = formBuilder.group({
      customerId: [this.customers.id],
      year: [new Date().getFullYear(), { validators: [Validators.required, Validators.minLength(4), NoBlank], updateOn: 'change' }],
      months: [[], { validators: [Validators.required], updateOn: 'change' }],
      file: [null, { validators: [Validators.required], updateOn: 'change' }]
    })
  }

  sendFile() {
    this.customerService.uploadFile(
      this.resourceForm.value.customerId, 
      this.resourceForm.value.year, 
      this.resourceForm.value.months, 
      this.resourceForm.value.file).subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.uploadProgress = Math.round(100 * event.loaded / event.total);
          }
        }, error: (error: any) => {
          console.log(error);  
        }
      })
  }

  onFileDropped(files: FileList | File) {
    if (files instanceof File) {
      this.file = files;
    }
  }

}
