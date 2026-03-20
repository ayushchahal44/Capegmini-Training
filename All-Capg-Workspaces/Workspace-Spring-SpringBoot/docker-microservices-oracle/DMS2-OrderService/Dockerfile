FROM eclipse-temurin:17-jdk
ENV TZ=UTC
WORKDIR /app
COPY target/DMS2-OrderService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 7072
ENTRYPOINT ["java","-jar","app.jar"]