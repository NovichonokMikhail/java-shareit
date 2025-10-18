##FROM amazoncorretto:11
#FROM ubuntu:latest
#VOLUME /tmp
#ARG JAR_FILE=target/*.jar
#COPY target app.jar
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]

#FROM amazoncorretto:11
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9091
#COPY target/*exec.jar shareit-server.jar
#ENTRYPOINT ["java","-jar","/shareit-server.jar"]

FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9091"
COPY target/*.jar shareit-server.jar
ENTRYPOINT ["java","-jar","/shareit-server.jar"]