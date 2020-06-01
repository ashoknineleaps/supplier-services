FROM openjdk:8
ARG JAR_FILE=supplier-service/target/*.jar
ARG PROP_FILE=application.properties
COPY ${JAR_FILE} supplier-service.jar
COPY ${PROP_FILE} application.properties
ENTRYPOINT ["java", "-jar", "/supplier-service.jar", "--spring.config.location=application.properties"]
EXPOSE 8083