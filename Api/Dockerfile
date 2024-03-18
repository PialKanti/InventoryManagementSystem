FROM gradle:jdk21-alpine AS build
WORKDIR /app
COPY /src/main ./src/main
COPY build.gradle .
COPY settings.gradle .
RUN gradle clean build

FROM tomcat
EXPOSE 8080
COPY --from=build /app/build/libs/inventorymanagementsystem-1.0.0.war /usr/local/tomcat/webapps