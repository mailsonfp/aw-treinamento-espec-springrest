FROM openjdk:16.0.1-jdk-oraclelinux8

WORKDIR /app

ARG JAR_FILE

COPY target/${JAR_FILE} /app/api.jar
COPY docker-entrypoint.sh /docker-entrypoint.sh

RUN chmod +x /docker-entrypoint.sh

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]