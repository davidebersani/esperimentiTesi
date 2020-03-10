########################################################
# Dockerfile per Eureka 
########################################################

FROM openjdk:8-jdk 

ADD build/libs/eureka-0.0.1-SNAPSHOT.jar eureka.jar

EXPOSE 8761

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "eureka.jar"]

