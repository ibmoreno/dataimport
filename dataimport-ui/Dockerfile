ARG NODE_VERSION=18.18.2

FROM node:${NODE_VERSION}-alpine as build

WORKDIR /usr/local/app
COPY ./ /usr/local/app/

RUN npm install
RUN npm run build

FROM nginx:latest
COPY --from=build /usr/local/app/dist/dataimport-ui/browser /usr/share/nginx/html
EXPOSE 80