import { HttpErrorResponse } from "@angular/common/http";
import { ErrorHandler, Injectable, Injector, NgZone } from "@angular/core";
import { ToastrService } from "ngx-toastr";


@Injectable({
    providedIn: 'root'
})
export class ApplicationErrorHandler implements ErrorHandler {

    private ms: ToastrService | any = null;

    constructor(private injector: Injector, private ngZone: NgZone) {

    }

    /* faz tratamento de erro global no sistema */
    handleError(error: any): void {

        this.ms = this.injector.get(ToastrService);

        this.ngZone.run(() => {

            if (error instanceof ErrorEvent) {

                this.ms.error('Requisição não processada. Notifique o administrador do sistema.',
                    'Erro na Requisição', { closeButton: true });
    
            } else if (error instanceof HttpErrorResponse) {

                const message = (error.error && error.error.detail) ? error.error.detail : error.message;

                switch (error.status) {

                    case 400:
                        this.ms.error(message || 'Soliticição inválida', 'Erro na requisição', { closeButton: true });
                        break;

                    case 401:
                        this.ms.error(message || 'Login ou Senha invalido!', 'Erro de Autenticação', { closeButton: true });
                        break;

                    case 403:
                        this.ms.error(message || 'Não autorizado', 'Erro na requisição', { closeButton: true });
                        break;

                    case 404:
                         this.ms.error(message || 'Recurso não encontrado. Verifique o console para mais detalhes.',
                             'Erro na requisição', { closeButton: true });
                        break;

                    default:
                        this.ms.error(message || 'Erro Interno no Servidor', 'Erro na requisição', { closeButton: true });
                        break;

                }

            } else {

                 this.ms.error('Requisição não processada. Notifique o administrador do sistema.',
                     'Erro na Requisição', { closeButton: true });
                console.log(error.message);

            }

        });
    }

}
