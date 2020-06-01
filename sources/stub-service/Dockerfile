FROM openjdk:8-jdk
ADD build/libs/stub-0.0.1-SNAPSHOT.jar stub.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "stub.jar"]

