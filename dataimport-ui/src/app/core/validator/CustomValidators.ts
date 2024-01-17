import { AbstractControl, FormGroup } from '@angular/forms';

// obriga a selecionar um item valido na lista do componente
// mat-autocomplete.

// exemplo: https://onthecode.co.uk/force-selection-angular-material-autocomplete/

export function RequireMatch(control: AbstractControl): { [key: string]: boolean } | null {
    const valor: any = control.value;
    if (typeof valor === 'string') {
        return { invalid: true };
    }

    return null;

}

export function NoBlank(control: AbstractControl): { [key: string]: boolean } | null {
    const valor: any = control.value;
    if (typeof valor === 'string') {
        if (valor.trim() === '') {
            return { invalid: true };
        }
    }

    return null;

}

export function CEP(control: AbstractControl): { [key: string]: boolean } | null {
    const valor: any = control.value;
    if (valor && valor !== '') {
        const validaCEP = /^[0-9]{8}$/;
        return validaCEP.test(valor) ? null : { invalid: true };
    }

    return null;

}

export function NumeroTelefone(control: AbstractControl): { [key: string]: boolean } | null {
    const valor: any = control.value;
    if (valor && valor !== '') {
        const numero = /^[0-9]{6,7}-[0-9]{4}$/;
        return numero.test(valor) ? null : { invalid: true };
    }

    return null;

}

export function equalsTo(control: AbstractControl, nameControl: string): { [key: string]: boolean } | null {


    /**
     * Exemplo:
     * confirmaSenha: [null, { validators: [(control) => equalsTo(control, 'senha')], updateOn: 'change' }]
     */

    if (!control.root || !(control.root as FormGroup).controls) {
        return null;
    }

    const valor: any = control.value;
    if (valor && valor !== '') {
        const form = (control.root as FormGroup).get(nameControl);
        if (valor !== form?.value) {
            return {senha: true};
        }

    }

    return null;

}
