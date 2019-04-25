FROM docker-registry.devops.sec.safran:80/openjdk:8-jdk

COPY ./target/mproducts-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch mproducts-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","mproducts-0.0.1-SNAPSHOT.jar"]
