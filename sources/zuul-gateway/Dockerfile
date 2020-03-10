########################################################
# Dockerfile per Zuul
########################################################

FROM openjdk:8-jdk 

ADD build/libs/zuul-0.0.1-SNAPSHOT.jar zuul.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "zuul.jar"]

