import { ApplicationConfig, LOCALE_ID, importProvidersFrom } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { BrowserModule } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { PreloadAllModules, provideRouter, withPreloading } from '@angular/router';
import { AppRoutes } from '@app/app.routes';
import { LABEL_MAT_PAGINATOR_PT } from '@core/i18n/pt/mat-paginator';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(AppRoutes, withPreloading(PreloadAllModules)),
    importProvidersFrom(BrowserModule),
    provideAnimations(),
    { provide: LOCALE_ID, useValue: 'pt' },
    { provide: MatPaginatorIntl, useValue: LABEL_MAT_PAGINATOR_PT() },
  ]
};
