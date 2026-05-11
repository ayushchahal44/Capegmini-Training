@echo off
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo [1/7] Starting Eureka Server...
start "Eureka Server" cmd /c "cd finflow-eureka-server && ..\mvnw.cmd spring-boot:run"
timeout /t 15

echo [2/7] Starting Auth Service...
start "Auth Service" cmd /c "cd finflow-auth-service && ..\mvnw.cmd spring-boot:run"

echo [3/7] Starting Application Service...
start "Application Service" cmd /c "cd finflow-application-service && ..\mvnw.cmd spring-boot:run"

echo [4/7] Starting Document Service...
start "Document Service" cmd /c "cd finflow-document-service && ..\mvnw.cmd spring-boot:run"

echo [5/7] Starting Admin Service...
start "Admin Service" cmd /c "cd finflow-admin-service && ..\mvnw.cmd spring-boot:run"

echo [6/7] Starting Notification Service...
start "Notification Service" cmd /c "cd finflow-notification-service && ..\mvnw.cmd spring-boot:run"

echo [7/7] Starting Gateway...
start "Gateway" cmd /c "cd finflow-gateway && ..\mvnw.cmd spring-boot:run"

echo All services are starting in separate windows. Please wait for them to initialize.
