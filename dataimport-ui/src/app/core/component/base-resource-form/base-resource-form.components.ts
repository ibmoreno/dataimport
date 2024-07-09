import { Injector, OnInit, ViewContainerRef, Directive } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IBaseFormDeactivateResolve } from '@app/core/resolver/ibase-form-deactivate.resolve';
import { ActionSystemModel } from '@app/core/util/action-system.model';
import { BaseEntity } from '@core/models/base-entity.model';
import { BaseResourceService } from '@core/services/base-resource.service';
import { TdDialogService } from '@covalent/core/dialogs';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';

@Directive()
export abstract class BaseResourceFormComponent<T extends BaseEntity> implements OnInit, IBaseFormDeactivateResolve {

    public resourceForm: FormGroup;
    public titleForm: string;
    public actionSystem: ActionSystemModel;
    public submittingForm = false;

    protected resolverData = 'resolverData';
    protected route: ActivatedRoute;
    protected router: Router;
    protected fb: FormBuilder;
    protected msg: ToastrService;
    protected viewContainerRef: ViewContainerRef;
    protected dialogService: TdDialogService;

    constructor(injector: Injector,
        public resource: T,
        protected resourceService: BaseResourceService<T>,
        protected jsonDataToResourceFn: (jsonData: any) => T
    ) {
        this.route = injector.get(ActivatedRoute);
        this.router = injector.get(Router);
        this.fb = injector.get(FormBuilder);
        this.actionSystem = new ActionSystemModel(this.route);
        this.msg = injector.get(ToastrService);
        this.dialogService = injector.get(TdDialogService);
        this.viewContainerRef = injector.get(ViewContainerRef);
        this.titleForm = this.route.snapshot.data['title'];
        this.resourceForm = this.buildResourceForm();
        this.loadResource();
    }

    ngOnInit(): void {
    }

    /**
     * Chamada para construção do formulario reativo
     */
    protected abstract buildResourceForm(): FormGroup;

    /**
     * Evento disparado apos atualizar dados no servidor
     *
     * @param action, ação realizada
     */
    protected abstract onAfterUpdate(resource: T): void;

    /**
     * Carrega dados na tela, no caso de edição vou vizualização
     */
    protected loadResource() {
        const resourceLocal = this.route.snapshot.data[this.resolverData];
        this.setDataForm(resourceLocal || this.resource);
        if (this.actionSystem.isView()) {
            this.resourceForm.disable();
        }
    }

    /**
     * Metodo para preencher os dados no formuário, podendo ser sobrescrito
     * para as classes que irão herdar deste form, permitindo a customização
     * dos dados ao preencher o FORM.
     */
    protected setDataForm(entity: T) {
        this.resourceForm.patchValue(entity);
    }

    /**
     * No caso de sair do formulario sem salvar os dados, notifica o usuário.
     */
    podeDesativar(): Observable<boolean> | boolean {

        if ((this.resourceForm.dirty) && (!this.submittingForm)) {

            return this.dialogService.openConfirm({
                title: 'Confirmar',
                message: 'Deseja cancelar as alterações feitas neste cadastro?',
                cancelButton: 'NÃO',
                acceptButton: 'SIM',
                width: '430px',
                viewContainerRef: this.viewContainerRef,
            }).afterClosed();

        } else {

            return true;

        }

    }

    submitForm() {

        this.submittingForm = true;
        if (this.actionSystem.isAdd()) {
            this.createResource();
        } else {
            this.updateResource();
        }

    }

    protected createResource() {
        const resource: T = this.jsonDataToResourceFn(this.resourceForm.value);
        this.resourceService
            .create(resource)
            .subscribe({
                next: resourceSub => this.actionsForSuccess(resourceSub),
                error: error => this.actionsForError(error)
            });
    }

    protected updateResource() {
        const resource: T = this.jsonDataToResourceFn(this.resourceForm.value);
        this.resourceService
            .update(resource)
            .subscribe({
                next: resourceSub => this.actionsForSuccess(resourceSub),
                error: error => this.actionsForError(error)
            });
    }

    protected deleteResource() {
        const resource: T = this.jsonDataToResourceFn(this.resourceForm.value);
        this.resourceService
            .delete(resource.id)
            .subscribe({
                next: () => this.actionsForSuccess(resource, 'Registro excluido com sucesso.'),
                error: error => this.actionsForError(error)
            });
    }

    delete(): void {

        this.dialogService.openConfirm({
            title: 'Confirmar',
            message: 'Confirma exclusão deste registro?',
            cancelButton: 'NÃO',
            acceptButton: 'SIM',
            width: '430px',
            viewContainerRef: this.viewContainerRef,
        }).afterClosed().subscribe((accept: boolean) => {

            if (accept) {
                this.deleteResource();
            }

        });

    }

    cancel(): void {
        const resource: T = this.jsonDataToResourceFn(this.resourceForm.value);
        this.onAfterUpdate(resource);
    }

    protected actionsForSuccess(resource: T, message: string = ''): void {
        this.msg.success(message || 'Registro salvo com sucesso!', 'Sucesso');
        this.onAfterUpdate(resource);
    }

    protected actionsForError(httpErrorResponse: any): void {

        this.submittingForm = false;
        if ((httpErrorResponse.status === 400) && (httpErrorResponse.error.violations)) {
            const violations: [] = httpErrorResponse.error.violations;
            violations.forEach((prop: { field: '', message: '' }) => {
                const formControl = this.resourceForm.get(prop.field);
                if (formControl) {

                    // adiciona mensagem de erro para o campo do formulario
                    formControl.setErrors({ invalid: true, message: prop.message });

                } else {

                    // caso não encontrar o campo de erro seta em detail para ser notificado
                    // na tela do formulário

                    httpErrorResponse.error.detail = prop.message;
                    throw httpErrorResponse;

                }
            });
        } else {

            throw httpErrorResponse;

        }

    }

}
