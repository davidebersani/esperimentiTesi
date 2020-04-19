FROM openjdk:8-jdk

ADD build/libs/gateway-0.0.1-SNAPSHOT.jar gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "gateway.jar"]

