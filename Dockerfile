FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/TM_BACK-SNAPSHOT-0.0.1.jar
COPY ${JAR_FILE} app_tomas_and_pets.jar

# Se define el profile bajo el que se creara el .jar en el docer-compose up
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_tomas_and_pets.jar"]