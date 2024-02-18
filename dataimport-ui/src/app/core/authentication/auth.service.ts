import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { KeycloakService } from "keycloak-angular";
import { KeycloakProfile } from 'keycloak-js';
import { from, Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private keycloakUrl: string;
    private redirectUri: string;
    private loggedIn: boolean = false
    private adminRole: boolean = false
    private realm: string;

    constructor(private readonly keycloak: KeycloakService) {
        this.redirectUri = environment.keycloak.redirectUri;
        this.keycloakUrl = environment.keycloak.url;
        this.realm = environment.keycloak.realm;
        this.loggedIn = this.keycloak.isLoggedIn();
        this.adminRole = this.keycloak.isUserInRole('admin');
    };


    logout(): void {
        this.keycloak.logout(this.redirectUri).then();
    }

    login() {
        this.keycloak.login({ redirectUri: this.redirectUri }).then();
    }


    isLoggedIn(): boolean {
        return this.keycloak.isLoggedIn();
    }

    getProfile(): Promise<KeycloakProfile | null> {
        if (this.loggedIn) {
            return this.keycloak.getKeycloakInstance().loadUserProfile()
        } else {
            return Promise.resolve(null);
        }
    }

    getTokenExpirationDate(): number {
        return (this.keycloak.getKeycloakInstance().refreshTokenParsed as { exp: number })['exp'] as number;
    }

    refresh(): Observable<any> {
        return from(this.keycloak.getKeycloakInstance().updateToken(1800));
    }

    isExpired(): boolean {
        return this.keycloak.getKeycloakInstance().isTokenExpired();
    }

    getAccountManagement() {
        this.keycloak.getKeycloakInstance().accountManagement();
    }

    isAdmin(): boolean {
        return this.adminRole;
    }

    getAdminConsole() {
        window.open(`${this.keycloakUrl}/admin/${this.realm}/console/`, '_blank');
    }
}
