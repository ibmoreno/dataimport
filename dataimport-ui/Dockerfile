# command line
# docker build -t ibmoreno/dataimport-ui:latest .
# docker push ibmoreno/dataimport-ui:latest

ARG NODE_VERSION=18.18.2

FROM node:${NODE_VERSION}-alpine as build

WORKDIR /usr/local/app
COPY ./ /usr/local/app/

RUN npm install
RUN npm run build

FROM nginx:1.25.3
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/local/app/dist/dataimport-ui/browser /usr/share/nginx/html
EXPOSE 80