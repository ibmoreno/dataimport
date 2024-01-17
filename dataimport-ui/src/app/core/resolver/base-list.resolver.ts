import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from "@angular/router";
import { BaseEntity } from "@app/core/models/base-entity.model";
import { BaseResourceService } from "@app/core/services/base-resource.service";
import { PageSort, PageableModel } from "@app/core/util/pageable.model";
import { Observable } from "rxjs";


export class BaseListResolver<T extends BaseEntity> {

    private page: PageableModel<T> = new PageableModel<T>();
    private router: Router;
    private resourceService: BaseResourceService<T>;

    constructor(
        router: Router,
        resourceService: BaseResourceService<T>) {
        this.router = router;
        this.resourceService = resourceService
    }

    protected resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<PageableModel<T>> | Promise<PageableModel<T>> | any {
        this.resolveRequestPage(route, this.page);
        return new Observable(observer => {
            this.resourceService.getPage(this.page).subscribe({
                next: (data) => {
                    observer.next(data)
                    observer.complete();
                },
                error: (error) => {
                    this.router.navigate(['']);
                    throw error
                }
            })
        });
    }

    resolveRequestPage(route: ActivatedRouteSnapshot, page: PageableModel<T>) {
        // resolve os parametros de paginação
        const queryParams = route.queryParams;
        if (queryParams) {
            page.number = queryParams['page'] ? queryParams['page'] : 0;
            page.size = queryParams['size'] ? queryParams['size'] : 10;
            if (queryParams['sort']) {
                page.sort = new PageSort();
                page.sort.field = queryParams['sort'];
                page.sort.order = queryParams['order'];
            }
        }

    }

}