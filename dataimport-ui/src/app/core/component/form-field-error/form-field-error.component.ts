import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl } from '@angular/forms';

@Component({
  selector: 'app-message-error',
  standalone: true,
  imports: [],
  template: `@if (hasError()) { <span>{{ messageError }}</span> }`,
})
export class FormFieldErrorComponent {

  @Input('form-control') 
  public formControl: FormControl<any> | any = [];

  constructor() { }

  public get messageError(): string | null {

      if (this.hasError()) {

          if (this.formControl.hasError('required') === true) {
              return 'Campo de preenchimento obrigatório';
          };
          
          if (this.formControl.hasError('email') === true) {
              return 'Formato de email inválido';
          }; 
          
          if (this.formControl.hasError('minlength') === true) {
              const requiredLength = this.formControl?.errors?.['minlength']?.['requiredLength'];
              return `Deve ter no mínimo ${requiredLength} caracteres`;
          };
          
          if (this.formControl.hasError('maxlength') === true) {
              const requiredLength = this.formControl?.errors?.['maxlength']?.['requiredLength'];
              return `Deve ter no máximo ${requiredLength} caracteres`;
          };
          
          if (this.formControl.hasError('Mask error') === true) {
              return ('Campo com formato inválido');
          };
          
          if (this.formControl.hasError('invalid') === true) {
              const msg = this.formControl?.errors?.['message'];
              return (msg || 'Campo com preenchimento inválido');
          };
          
          if (this.formControl.hasError('senha') === true) {
              return 'Senha não confere!';
          }

      }

      return null;

  }

  public hasError(): boolean {
      return this.formControl.invalid &&
          (this.formControl.dirty || this.formControl.touched);
  }

}
