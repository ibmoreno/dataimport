import { HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { APP_INITIALIZER, ApplicationConfig, ErrorHandler, LOCALE_ID, importProvidersFrom } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { PreloadAllModules, provideRouter, withPreloading } from '@angular/router';
import { AppRoutes } from '@app/app.routes';
import { ApplicationErrorHandler } from '@core/error/app.error-handler';
import { LABEL_MAT_PAGINATOR_PT } from '@core/i18n/pt/mat-paginator';
import { LoadingBarHttpClientModule } from '@ngx-loading-bar/http-client';
import { environment } from 'environments/environment';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { provideEnvironmentNgxMask } from 'ngx-mask';
import { provideToastr } from 'ngx-toastr';

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: environment.keycloak.url,
        realm: environment.keycloak.realm,
        clientId: environment.keycloak.clientId
      },
      initOptions: {
        onLoad: 'check-sso'
      },
      enableBearerInterceptor: true,
      bearerExcludedUrls: ['/assets/']
    });
}


export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(AppRoutes, withPreloading(PreloadAllModules)),
    importProvidersFrom(BrowserModule, KeycloakAngularModule, LoadingBarHttpClientModule, HttpClientModule),
    provideHttpClient(withInterceptorsFromDi()),
    provideAnimations(),
    provideEnvironmentNgxMask(),
    provideToastr({ closeButton: true }),
    { provide: LOCALE_ID, useValue: 'pt' },
    { provide: MatPaginatorIntl, useValue: LABEL_MAT_PAGINATOR_PT() },
    { provide: ErrorHandler, useClass: ApplicationErrorHandler },
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    }
  ]
};
