import { HttpClient, HttpParams } from "@angular/common/http";
import { Injector } from "@angular/core";
import { BaseEntity } from "@app/core/models/base-entity.model";
import { PageableModel } from "@app/core/util/pageable.model";
import { Observable, throwError } from "rxjs";
import { first, map } from "rxjs/operators";


export abstract class BaseResourceService<T extends BaseEntity> {

    protected http: HttpClient;

    constructor(
        protected apiPath: string,
        injector: Injector,
        protected jsonDataToResourceFn: (jsonData: any) => T
    ) {
        this.http = injector.get(HttpClient);
    }

    getAll(): Observable<T[]> {
        return this.http.get<T[]>(this.apiPath).pipe(first(),
            map(this.jsonDataToResources.bind(this))
        );
    }

    getPage(page: PageableModel<T>): Observable<PageableModel<T>> {
        return this.page(new HttpParams(), this.apiPath, page).pipe(first());
    }

    getById(codigo: any): Observable<T> {
        const url = `${this.apiPath}/${codigo}`;
        return this.http.get<T>(url).pipe(first(),
            map(this.jsonDataToResource.bind(this))
        );
    }

    create(resource: T): Observable<T> {
        return this.http.post<T>(this.apiPath, resource).pipe(first(),
            map(() => {
                this.notifyUpdate();
                return this.jsonDataToResource(this)
            })
        );
    }

    update(resource: T): Observable<T> {
        const url = `${this.apiPath}/${resource.id}`;
        return this.http.put<T>(url, resource).pipe(first(),
            map(() => {
                this.notifyUpdate();
                return resource;
            })
        );
    }

    delete(id: any): Observable<any> {
        const url = `${this.apiPath}/${id}`;
        return this.http.delete<any>(url).pipe(first(), map(() => {
            this.notifyUpdate();
            return null;
        }));
    }

    query(filter: any, apiPath = this.apiPath): Observable<T[]> {
        const options = filter ? { params: filter } : {};
        return this.http.get<T[]>(this.apiPath, options).pipe(first(),
            map(this.jsonDataToResources.bind(this))
        );
    }

    page(filter: HttpParams, apiPath: string, page: PageableModel<T>): Observable<PageableModel<T>> {
        filter = this.requestPageable(filter, page);
        return this.http.get<PageableModel<T>>(apiPath, { params: filter });

    }

    createRequestPageable(page: PageableModel<T>): HttpParams {
        return this.requestPageable(new HttpParams(), page);
    }

    protected requestPageable(options: HttpParams, page: PageableModel<T>): HttpParams {
        options = options.append('page', page.number.toString())
            .append('size', page.size.toString());

        if (page.sort) {
            if (page.sort.field) {
                options = options.set('sort', page.sort.field + ',' + page.sort.order);
            }
        }

        return options;
    }

    protected jsonDataToResources(jsonData: any[]): T[] {
        const resources: T[] = [];
        jsonData.forEach(
            element => resources.push(this.jsonDataToResourceFn(element))
        );
        return resources;
    }

    protected jsonDataToResource(jsonData: any): T {
        return this.jsonDataToResourceFn(jsonData);
    }

    /**
     * Sobrescrever esse método caso necessite notificar os eventos
     * de create, update e delete do Recurso Chamado.
     *
     * EventEmitter<T>
     *
     */
    protected notifyUpdate() {
    }

    /**
     * Sobrescrever esse método caso necessite tratar o
     * erro ocorrido de forma local ou se desejar adicionar
     * algum tratamento no erro antes de enviá-lo para o handleError
     * global da aplicação.
     *
     */
    protected handleError(error: any): Observable<any> {
        return throwError(() => new Error(error));
    }


}
