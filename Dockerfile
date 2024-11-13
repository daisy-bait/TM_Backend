FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/TM_BACK-SNAPSHOT-0.0.1.jar
COPY ${JAR_FILE} app_tomas_and_pets.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_tomas_and_pets.jar"]