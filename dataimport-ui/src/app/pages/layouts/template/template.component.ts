import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatIconRegistry } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DomSanitizer } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AuthService } from '@app/core/authentication/auth.service';
import { CovalentLayoutModule } from '@covalent/core/layout';
import { KeycloakProfile } from 'keycloak-js';

@Component({
  selector: 'app-template',
  standalone: true,
  imports: [CommonModule, CovalentLayoutModule, MatListModule, MatIconModule, MatButtonModule, MatTooltipModule, RouterModule],
  templateUrl: './template.component.html',
  styleUrl: './template.component.scss'
})
export class TemplateComponent implements OnInit {

  protected userProfile: KeycloakProfile | null = null;
  protected isAdmin: boolean = false;

  constructor(
    private iconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    private authService: AuthService,
  ) {

    this.iconRegistry.addSvgIconInNamespace('assets',
      'dataimport-logo', this.domSanitizer.bypassSecurityTrustResourceUrl('/assets/dataimport.svg'));

  }
  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin()
    this.authService.getProfile().then(profile => this.userProfile = profile );
  }
  
  public openAccountManager() {
    this.authService.getAccountManagement();
  }

  public logout(): void {
    this.authService.logout();
  }

  public openAdminConsole(): void {
    this.authService.getAdminConsole();
  }

}
