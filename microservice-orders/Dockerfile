FROM docker-registry.devops.sec.safran:80/openjdk:8-jdk

COPY ./target/morders-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch morders-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","morders-0.0.1-SNAPSHOT.jar"]
