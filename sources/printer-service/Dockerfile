
FROM openjdk:8-jdk 
RUN apt-get update && apt-get install -y curl
ADD build/libs/printer-0.0.1-SNAPSHOT.jar printer.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "printer.jar"]

HEALTHCHECK --start-period=30s --interval=10s CMD curl -f http://localhost:8080/actuator/health || exit 1
