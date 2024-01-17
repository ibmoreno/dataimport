import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { BaseEntity } from '@core/models/base-entity.model';
import { BaseResourceService } from '@core/services/base-resource.service';
import { Observable } from 'rxjs';


export abstract class BaseFormResolve<T extends BaseEntity> {

    private router: Router;
    private resourceService: BaseResourceService<T>;

    constructor(
        router: Router,
        resourceService: BaseResourceService<T>,
        protected jsonDataToResourceFn: (jsonData: any) => T) {
        this.router = router;
        this.resourceService = resourceService;
    }

    protected resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<T> | Promise<T> | any {
        return new Observable(observer => {
            const entity: T | null = this.resolveState();
            if (entity) {
                observer.next(entity);
                observer.complete();
            } else {
                const id = route.params['id'];
                this.resourceService.getById(id).subscribe({
                    next: (data) => {
                        observer.next(data);
                        observer.complete();
                    },
                    error: (error) => {
                        observer.error(error);
                        this.router.navigate(['']);
                    }
                })
            }
        });
    }

    /**
     * verifica se o objeto existe na propriedade state da navegação,
     * caso existir retorna ele sem a necessidade de fazer requisição
     * no banco de dados.
     */
    private resolveState(): T | null {
        const navigate = this.router.getCurrentNavigation();
        if (navigate?.extras && navigate.extras.state) {
            return this.jsonDataToResourceFn(navigate.extras.state['entity']);
        }

        return null;
    }

}
