FROM docker-registry.devops.sec.safran:80/openjdk:8u151-alpine

COPY ./target/clientui-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch clientui-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","clientui-0.0.1-SNAPSHOT.jar"]
