FROM eclipse-temurin:17-jdk
ENV TZ=UTC
WORKDIR /app
COPY target/DMS1-CustomerService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 7071
ENTRYPOINT ["java","-jar","app.jar"]