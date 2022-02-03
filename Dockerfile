FROM eclipse-temurin:17

RUN mkdir -p /opt/app
COPY target/sbanken.jar /opt/app
CMD ["java", "-jar", "/opt/app/sbanken.jar"]