export const environment = {
    production: false,
    keycloak: {
        redirectUri: 'http://localhost:4200',
        url: 'http://kubernetes.docker.internal.io:8180',
        realm: 'dataimport',
        clientId: 'login-client-dataimport'
    }
};