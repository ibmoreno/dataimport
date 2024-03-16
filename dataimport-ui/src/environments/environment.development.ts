export const environment = {
    production: false,
    keycloak: {
        redirectUri: 'http://localhost:4200',
        url: 'http://kubernetes.docker.internal.org:8180',
        realm: 'dataimport',
        clientId: 'login-client-dataimport'
    }
};