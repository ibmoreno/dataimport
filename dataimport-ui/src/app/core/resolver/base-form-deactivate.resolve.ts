import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IBaseFormDeactivateResolve } from './ibase-form-deactivate.resolve';

@Injectable({
    providedIn: 'root'
})
export class BaseFormDeactivateResolve<T> {

    canDeactivate(component: IBaseFormDeactivateResolve): Observable<boolean> | boolean {

        return component.podeDesativar();

    }
}
