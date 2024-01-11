import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DomSanitizer } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { CovalentLayoutModule } from '@covalent/core/layout';

@Component({
  selector: 'app-template',
  standalone: true,
  imports: [CommonModule, CovalentLayoutModule, MatListModule, MatIconModule, MatButtonModule, MatTooltipModule, RouterModule],
  templateUrl: './template.component.html',
  styleUrl: './template.component.scss'
})
export class TemplateComponent {

  constructor(
    private iconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer
  ) {

    this.iconRegistry.addSvgIconInNamespace('assets',
      'dataimport-logo', this.domSanitizer.bypassSecurityTrustResourceUrl('/assets/dataimport.svg'));

  }

}
