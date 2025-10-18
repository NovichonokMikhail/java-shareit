#FROM amazoncorretto:11
#FROM ubuntu:latest
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9090
#COPY target/*.jar shareit-gateway.jar
#ENTRYPOINT ["java","-jar","/shareit-gateway.jar"]

FROM eclipse-temurin:21-jdk
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9090"
COPY target/*.jar shareit-gateway.jar
ENTRYPOINT ["java","-jar","/shareit-gateway.jar"]