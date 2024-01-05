import { ApplicationConfig, LOCALE_ID } from '@angular/core';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { provideAnimations } from '@angular/platform-browser/animations';
import { PreloadAllModules, provideRouter, withPreloading } from '@angular/router';
import { LABEL_MAT_PAGINATOR_PT } from '@core/i18n/pt/mat-paginator';
import { AppRoutes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(AppRoutes, withPreloading(PreloadAllModules)),
    provideAnimations(),
    { provide: LOCALE_ID, useValue: 'pt' },
    { provide: MatPaginatorIntl, useValue: LABEL_MAT_PAGINATOR_PT() },
  ]
};
