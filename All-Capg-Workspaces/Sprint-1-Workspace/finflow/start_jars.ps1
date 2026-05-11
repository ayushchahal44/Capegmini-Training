# Stop all existing java processes
Stop-Process -Name java -ErrorAction SilentlyContinue
Write-Host "Stopped all Java processes." -ForegroundColor Yellow

$javaHome = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$env:JAVA_HOME = $javaHome
$env:PATH = "$javaHome\bin;$env:PATH"

$services = @(
    @{ name = "Eureka Server"; dir = "finflow-eureka-server"; jar_pattern = "finflow-eureka-server-*.jar" },
    @{ name = "Auth Service"; dir = "finflow-auth-service"; jar_pattern = "finflow-auth-service-*.jar" },
    @{ name = "Application Service"; dir = "finflow-application-service"; jar_pattern = "finflow-application-service-*.jar" },
    @{ name = "Document Service"; dir = "finflow-document-service"; jar_pattern = "finflow-document-service-*.jar" },
    @{ name = "Admin Service"; dir = "finflow-admin-service"; jar_pattern = "finflow-admin-service-*.jar" },
    @{ name = "Notification Service"; dir = "finflow-notification-service"; jar_pattern = "finflow-notification-service-*.jar" },
    @{ name = "Gateway"; dir = "finflow-gateway"; jar_pattern = "finflow-gateway-*.jar" }
)

foreach ($service in $services) {
    Write-Host "Starting $($service.name)..." -ForegroundColor Cyan
    $jar = Get-ChildItem -Path "$($service.dir)\target\$($service.jar_pattern)" | Sort-Object Length -Descending | Select-Object -First 1
    
    if ($jar) {
        $jarPath = $jar.FullName
        Write-Host "Using JAR: $($jar.Name)" -ForegroundColor Gray
        Start-Process java -ArgumentList "-jar `"$jarPath`"" -WindowStyle Hidden
    } else {
        Write-Host "ERROR: No JAR found for $($service.name) in $($service.dir)\target\" -ForegroundColor Red
    }
    
    if ($service.name -eq "Eureka Server") {
        Write-Host "Waiting 15s for Eureka..." -ForegroundColor Gray
        Start-Sleep -s 15
    }
}

Write-Host "All services started using JARs." -ForegroundColor Green
