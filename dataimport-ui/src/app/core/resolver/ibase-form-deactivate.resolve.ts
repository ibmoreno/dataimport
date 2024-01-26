import { Observable } from 'rxjs';

/**
 * Interface para ser implementa nos formularios de cadastro
 */
export interface IBaseFormDeactivateResolve {

    podeDesativar(): Observable<boolean> | boolean;

}
