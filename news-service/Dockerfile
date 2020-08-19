FROM openjdk:11-jre-slim

ARG SOURCE_JAR_ROOT=target
COPY news-service/target/news-service.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
