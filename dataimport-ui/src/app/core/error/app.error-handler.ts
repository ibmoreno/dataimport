import { HttpErrorResponse } from "@angular/common/http";
import { ErrorHandler, Injectable, Injector, NgZone } from "@angular/core";
import { ToastrService } from "ngx-toastr";


@Injectable({
    providedIn: 'root'
})
export class ApplicationErrorHandler extends ErrorHandler {

    private ms: ToastrService;

    constructor(private injector: Injector, private ngZone: NgZone) {
        super();
        this.ms = this.injector.get(ToastrService);
    }

    /* faz tratamento de erro global no sistema */
    override handleError(erroResponse: HttpErrorResponse | any): void {

        this.ngZone.run(() => {

            if (erroResponse instanceof ErrorEvent) {

                this.ms.error('Requisição não processada. Notifique o administrador do sistema.',
                    'Erro na Requisição', { closeButton: true });
                super.handleError(erroResponse);

            } else if (erroResponse instanceof HttpErrorResponse) {

                const message = (erroResponse.error && erroResponse.error.detail) ? erroResponse.error.detail : erroResponse.message;

                switch (erroResponse.status) {

                    case 400:
                        this.ms.error(message || 'Soliticição inválida', 'Erro na requisição', { closeButton: true });
                        super.handleError(erroResponse);
                        break;

                    case 401:
                        this.ms.error(message || 'Login ou Senha invalido!', 'Erro de Autenticação', { closeButton: true });
                        super.handleError(erroResponse);
                        break;

                    case 403:
                        this.ms.error(message || 'Não autorizado', 'Erro na requisição', { closeButton: true });
                        super.handleError(erroResponse);
                        break;

                    case 404:
                        this.ms.error(message || 'Recurso não encontrado. Verifique o console para mais detalhes.',
                            'Erro na requisição', { closeButton: true });
                        super.handleError(erroResponse);
                        break;

                    default:
                        this.ms.error(message || 'Erro Interno no Servidor', 'Erro na requisição', { closeButton: true });
                        super.handleError(erroResponse);
                        break;

                }

            } else {

                this.ms.error('Requisição não processada. Notifique o administrador do sistema.',
                    'Erro na Requisição', { closeButton: true });

                super.handleError(erroResponse);

            }

        });
    }

}
    
