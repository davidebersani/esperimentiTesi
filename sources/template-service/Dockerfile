
FROM openjdk:8-jdk 
RUN apt-get update && apt-get install -y curl
ADD build/libs/template-0.0.1-SNAPSHOT.jar template.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "template.jar"]

