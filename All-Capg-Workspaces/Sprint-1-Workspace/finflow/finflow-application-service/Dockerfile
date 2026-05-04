# Single stage: Runtime
FROM eclipse-temurin:17-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app
COPY finflow-application-service/target/*.jar app.jar
EXPOSE 8082
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
    CMD curl -f http://127.0.0.1:8082/actuator/health || exit 1
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
