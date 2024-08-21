FROM openjdk:17
COPY target/multipledatasource-0.0.1-SNAPSHOT.jar multipledbapp.jar
ENTRYPOINT ["java","-jar", "/multipledbapp.jar"]