ARG CR_URL
FROM $CR_URL/openjdk:8-jdk-alpine as base


# Environment variables
ENV DATABASE_SERVER ${DATABASE_SERVER:-NOT_DEFINED}
ENV DATABASE_USER ${DATABASE_USER:-NOT_DEFINED}
ENV DATABASE_PASS ${DATABASE_PASS:-NOT_DEFINED}
ENV DATABASE_DB ${DATABASE_DB:-NOT_DEFINED}


FROM $CR_URL/maven:3.6.2-jdk-8 as build

# Parameters related to the Maven settings.xml file
ARG NEXUS_USR_READ
ARG NEXUS_PSW_READ
ARG NEXUS_URL_MVN

# Create app directory
WORKDIR /app
COPY ./.m2 ./.m2

COPY ./pom.xml ./pom.xml

COPY ./commons/pom.xml ./commons/pom.xml
COPY ./core/pom.xml ./core/pom.xml
COPY ./api/pom.xml ./api/pom.xml

RUN mvn -B -ntp -s .m2/settings.xml prepare-package

COPY ./commons/ ./commons/
COPY ./core/ ./core/
COPY ./api/ ./api/

RUN mvn -B -ntp -s .m2/settings.xml package -DskipTests

FROM base as final
EXPOSE 8080 
COPY --from=build /app/api/target/*.jar /app.jar


ENTRYPOINT ["java", "-jar", "/app.jar"]

# Run image with additional arguments
# docker build -t <imagename>:latest .
# custom port
# docker run -d -p 8085:<tomcat port>  <imagename>  --server.port=<tomcat port>
#
# if you don't want to change tomcat port then:
# docker run -d -p 8085:8090 <imagename> 
