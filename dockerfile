FROM registry.access.redhat.com/ubi8/openjdk-21:1.19
COPY ./target/ms-practica-user-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

