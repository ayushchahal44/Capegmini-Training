# Stop all existing java processes
Stop-Process -Name java -ErrorAction SilentlyContinue
Write-Host "Stopped all Java processes." -ForegroundColor Yellow

$javaHome = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$services = @(
    @{ name = "Eureka Server"; dir = "finflow-eureka-server" },
    @{ name = "Auth Service"; dir = "finflow-auth-service" },
    @{ name = "Application Service"; dir = "finflow-application-service" },
    @{ name = "Document Service"; dir = "finflow-document-service" },
    @{ name = "Admin Service"; dir = "finflow-admin-service" },
    @{ name = "Notification Service"; dir = "finflow-notification-service" },
    @{ name = "Gateway"; dir = "finflow-gateway" }
)

foreach ($service in $services) {
    Write-Host "Starting $($service.name)..." -ForegroundColor Cyan
    # Use quotes around JAVA_HOME because of spaces
    $args = "/c set `"JAVA_HOME=$javaHome`" && cd $($service.dir) && ..\mvnw.cmd spring-boot:run"
    Start-Process cmd -ArgumentList $args -WindowStyle Hidden
    
    if ($service.name -eq "Eureka Server") {
        Write-Host "Waiting 15s for Eureka..." -ForegroundColor Gray
        Start-Sleep -s 15
    }
}

Write-Host "All services started in background." -ForegroundColor Green
