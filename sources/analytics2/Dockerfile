FROM openjdk:8-jdk
ADD build/libs/analytics2-0.0.1-SNAPSHOT.jar analytics2.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "analytics2.jar"]

