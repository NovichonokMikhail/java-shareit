FROM amazoncorretto:11
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY target app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]