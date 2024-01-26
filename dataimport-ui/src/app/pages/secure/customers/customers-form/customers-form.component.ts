import { CommonModule } from '@angular/common';
import { Component, Injector } from '@angular/core';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { BaseResourceFormComponent } from '@app/core/component/base-resource-form/base-resource-form.components';
import { NoBlank } from '@app/core/validator/CustomValidators';
import { Customers } from '../service/customers.model';
import { CustomersService } from '../service/customers.service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormFieldErrorComponent } from '@app/core/component/form-field-error/form-field-error.component';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { CovalentDialogsModule } from '@covalent/core/dialogs';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgxMaskDirective } from 'ngx-mask';

@Component({
  selector: 'app-customers-form',
  standalone: true,
  imports: [CommonModule, 
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
    NgxMaskDirective,
  ],
  templateUrl: './customers-form.component.html',
  styleUrl: './customers-form.component.scss'
})
export class CustomersFormComponent extends BaseResourceFormComponent<Customers> {

  constructor(
    protected injector: Injector,
    protected customersService: CustomersService,

  ) {
    super(injector, Customers.getInstace(), customersService, Customers.fromJson);
  }

  protected override buildResourceForm(): FormGroup {
    return this.fb.group({
      id: [],
      name: [, { validators: [Validators.required, NoBlank], updateOn: 'change' }],
      cnpj: [],
      address: [],
      number: [],
      complement: [],
      city: [],
      state: [],
      zipCode: [],
      email: [ { validators: [Validators.email], updateOn: 'change' } ],
      phone: [],
      active: [ { validators: [Validators.required], updateOn: 'change' }],
      readModelVersion: [],
      createdAt: [],
      updatedAt: [],
    })
  }
  protected override onAfterUpdate(resource: Customers): void {
    if (this.actionSystem.isAlter()) {
      this.router.navigateByUrl(`/customers/${resource.id}/view`);
    } else {
      this.router.navigateByUrl('/customers');
    }
  }

}
