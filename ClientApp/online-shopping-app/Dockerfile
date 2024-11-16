# build stage
FROM node:18-alpine AS build-stage
WORKDIR /app
COPY package*.json ./
COPY vite.config.mjs ./
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine AS production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY ./ngnix/nginx.conf /etc/nginx/conf.d/
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]