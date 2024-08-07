import { CommonModule } from '@angular/common';
import { Component, Injector, ViewContainerRef } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
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
import { ToastrService } from 'ngx-toastr';
import { CovalentDialogsModule, TdDialogService } from '@covalent/core/dialogs';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    selector: 'app-customers-import',
    standalone: true,
    templateUrl: './customers-import.component.html',
    styleUrl: './customers-import.component.scss',
    imports: [
        CommonModule,
        CovalentDialogsModule,
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
        MatTooltipModule,
        FormFieldErrorComponent,
    ]
})
export class CustomersImportComponent {

  protected customers: Customers = new Customers();
  protected viewContainerRef: ViewContainerRef;
  protected dialogService: TdDialogService;
  protected submittingForm: boolean = false;
  protected resourceForm: FormGroup;
  protected uploadProgress: number = 0;
  protected title: string = '';

  constructor(
    protected route: ActivatedRoute,
    protected formBuilder: FormBuilder,
    protected toastr: ToastrService,
    protected router: Router,
    protected customerService: CustomersService,
    protected injector: Injector,
  ) {
    this.dialogService = injector.get(TdDialogService);
    this.viewContainerRef = injector.get(ViewContainerRef);
    this.customers = this.route.snapshot.data['resolverData'];
    this.title = 'IMPORTAR PLANILHA - ' + this.customers.name;
    this.resourceForm = formBuilder.group({
      customerId: [this.customers.id],
      year: [new Date().getFullYear(), { validators: [Validators.required, Validators.minLength(4), NoBlank], updateOn: 'change' }],
      months: [[new Date().getMonth() + 1], { validators: [Validators.required], updateOn: 'change' }],
      file: [null, { validators: [Validators.required], updateOn: 'change' }]
    })
  }

  sendFile() {
    this.submittingForm = true;
    this.customerService.uploadFile(
      this.resourceForm.value.customerId, 
      this.resourceForm.value.year, 
      this.resourceForm.value.months, 
      this.resourceForm.value.file).subscribe({
        next: (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.uploadProgress = Math.round(100 * event.loaded / event.total);
          }
        }, 
        complete: () => {
          this.submittingForm = false;
          this.uploadProgress = 0;
          this.toastr.success('Upload realizado com sucesso!');
          this.router.navigateByUrl('/customers');
        },
        error: (error: any) => {
          this.submittingForm = false;
          this.uploadProgress = 0;
          throw new Error(error);
        },
      })
  }

  onFileDropped(files: FileList | File) {
    if (files instanceof File) {
      this.resourceForm.patchValue({
        file: files
      })
    }
  }

  compareMonths = (o1: any, o2: any) => {
    return o1 == o2;
  }


  delete(): void {

    this.dialogService.openConfirm({
        title: 'Confirmar',
        message: `Confirma exclusão dos movimentos importados dos mes(es): ${this.resourceForm.value.months}, do ano de ${this.resourceForm.value.year}?`,
        cancelButton: 'NÃO',
        acceptButton: 'SIM',
        width: '430px',
        viewContainerRef: this.viewContainerRef,
    }).afterClosed().subscribe((accept: boolean) => {

        if (accept) {
          this.customerService.deleteMoviments(this.customers.id, this.resourceForm.value.year, this.resourceForm.value.months).subscribe({
            complete: () => {
              this.submittingForm = false;
              this.toastr.success('Movimentos removido com sucesso!');
              this.router.navigateByUrl('/customers');
            },
            error: (error: any) => {
              this.submittingForm = false;
              throw new Error(error);
            },
          })
        }
    });

}


}