# Start All FinFlow Backend Services
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"

Write-Host "Starting Eureka Server..."
Start-Process "java" "-jar finflow-eureka-server/target/finflow-eureka-server-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/eureka.log" -RedirectStandardError "logs/eureka_error.log"
Start-Sleep -Seconds 15

Write-Host "Starting Auth Service..."
Start-Process "java" "-jar finflow-auth-service/target/finflow-auth-service-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/auth.log" -RedirectStandardError "logs/auth_error.log"

Write-Host "Starting Application Service..."
Start-Process "java" "-jar finflow-application-service/target/finflow-application-service-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/application.log" -RedirectStandardError "logs/application_error.log"

Write-Host "Starting Document Service..."
Start-Process "java" "-jar finflow-document-service/target/finflow-document-service-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/document.log" -RedirectStandardError "logs/document_error.log"

Write-Host "Starting Admin Service..."
Start-Process "java" "-jar finflow-admin-service/target/finflow-admin-service-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/admin.log" -RedirectStandardError "logs/admin_error.log"

Write-Host "Starting Notification Service..."
Start-Process "java" "-jar finflow-notification-service/target/finflow-notification-service-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/notification.log" -RedirectStandardError "logs/notification_error.log"

Write-Host "Starting Gateway..."
Start-Process "java" "-jar finflow-gateway/target/finflow-gateway-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/gateway.log" -RedirectStandardError "logs/gateway_error.log"

Write-Host "All services started. Checking logs for details."
