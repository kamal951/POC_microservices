FROM docker-registry.devops.sec.safran:80/openjdk:8-jdk

COPY ./target/mpayment-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch mpayment-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","mpayment-0.0.1-SNAPSHOT.jar"]
