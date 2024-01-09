import { Directive, Injector, OnDestroy, OnInit } from "@angular/core";
import { PageEvent } from "@angular/material/paginator";
import { Sort } from '@angular/material/sort';
import { ActivatedRoute, Router } from "@angular/router";
import { BaseEntity } from "@app/core/models/base-entity.model";
import { PageSort, PageableModel } from "@app/core/util/pageable.model";
import { Subscription } from "rxjs";
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

export interface IColumnDefinitions {
    propertyName: string;
    showHandset: boolean;
}

@Directive()
export abstract class BaseResourceListComponent<T extends BaseEntity> implements OnInit, OnDestroy {
    
    public pageableModel: PageableModel<T> = new PageableModel();
    public titleForm: string = '';
    public sort: PageSort;
    public pageSizeOptions: number[] = [10, 25, 30, 50, 100];
    public isHandset: boolean = false;
    
    protected route: ActivatedRoute;
    protected router: Router;
    protected textSearch: string = '';
    protected columnDefinitions: IColumnDefinitions[] = [];

    private breakpointObserver: BreakpointObserver;
    private inscricaoResolveData: Subscription;

    constructor(injector: Injector) {

        this.route = injector.get(ActivatedRoute);
        this.router = injector.get(Router);
        this.breakpointObserver = injector.get(BreakpointObserver);

        // se inscreve para pegar os dados do resolve
        this.inscricaoResolveData = this.route.data.subscribe((resolve) => {
            this.pageableModel = resolve['resolverData'];
            this.titleForm = resolve['title'];
            this.routeEvent(resolve);
        });

        this.sort = { field: '', order: '' };

    }

    ngOnInit() {
        this.breakpointObserver.observe(Breakpoints.Handset).subscribe(result => {
            this.isHandset = result.matches;
        });
    }

    // pagina resultado
    pageEvent(pageEvent: PageEvent): void {
        this.pageableModel.number = pageEvent.pageIndex;
        this.pageableModel.size = pageEvent.pageSize;
        this.getData(this.pageableModel);
    }

    // ordena e pagina resultado
    sortEvent(sortEvent: Sort): void {
        this.sort.field = sortEvent.active;
        this.sort.order = sortEvent.direction;
        this.pageableModel.sort = this.sort;
        this.getData(this.pageableModel);
    }

    // pesquisa e pagina resultado
    searchEvent(event: string) {
        this.pageableModel.number = 0;
        this.textSearch = event;
        this.search(this.textSearch, this.pageableModel);
    }

    // busca dados paginado no servidor
    protected getData(pageableModel: PageableModel<T>) {

        if (this.textSearch) {

            // pesquisa e pagina resultado
            this.search(this.textSearch, pageableModel);

        } else {

            const params = {
                page: pageableModel.number,
                size: pageableModel.size,
                sort: pageableModel.sort ? pageableModel.sort.field : null,
                order: pageableModel.sort ? pageableModel.sort.order : null,
                sessionId: new Date().getTime()
            };

            // pagina resolvida pelo router, caso tiver parametros extra resolver também
            this.router.navigate([], { relativeTo: this.route, queryParams: params });

        }

    }

    getDisplayColumns(): string[] {
        if (this.isHandset) {
            return this.columnDefinitions
                .filter((column) => column.showHandset)
                .map((column) => column.propertyName);
        } else {
            return this.columnDefinitions
                .map((column) => column.propertyName);
        }
    }

    // metodo para pesquisar
    protected abstract search(searchText: string, pageableModel: PageableModel<T>): void;

    // metodo para sobrescrita caso precisar ser utlizado apos
    // processar a rota
    protected routeEvent(resolve: any): void {
    }

    ngOnDestroy(): void {
        // desinscreve do resolve
        this.inscricaoResolveData.unsubscribe();
    }




}