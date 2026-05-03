# Start All FinFlow Backend Services
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"

function Wait-ForPort {
    param([int]$Port, [int]$TimeoutSeconds = 120)
    Write-Host "Waiting for port $Port to open..." -ForegroundColor Cyan
    $endTime = (Get-Date).AddSeconds($TimeoutSeconds)
    while ((Get-Date) -lt $endTime) {
        $connection = New-Object System.Net.Sockets.TcpClient
        try {
            $connection.Connect("localhost", $Port)
            $connection.Close()
            Write-Host "Port $Port is open." -ForegroundColor Green
            return $true
        } catch {
            # Still waiting
        } finally {
            if ($connection) { $connection.Dispose() }
        }
        Start-Sleep -Seconds 2
    }
    Write-Host "Timeout waiting for port $Port." -ForegroundColor Red
    return $false
}

# Clean old logs
# Remove-Item -Path "logs/*.log" -ErrorAction SilentlyContinue

Write-Host "Starting Eureka Server..." -ForegroundColor Yellow
Start-Process "java" "-jar finflow-eureka-server/target/finflow-eureka-server-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/eureka.log" -RedirectStandardError "logs/eureka_error.log"

if (-not (Wait-ForPort -Port 8761 -TimeoutSeconds 120)) {
    Write-Host "Eureka failed to start. Aborting." -ForegroundColor Red
    exit 1
}

Write-Host "Starting Core Services..." -ForegroundColor Yellow
$services = @(
    @{ name = "Auth"; jar = "finflow-auth-service"; port = 8081 },
    @{ name = "Application"; jar = "finflow-application-service"; port = 8082 },
    @{ name = "Document"; jar = "finflow-document-service"; port = 8083 },
    @{ name = "Admin"; jar = "finflow-admin-service"; port = 8084 },
    @{ name = "Notification"; jar = "finflow-notification-service"; port = 8085 }
)

foreach ($service in $services) {
    Write-Host "Starting $($service.name) Service..." -ForegroundColor Yellow
    Start-Process "java" "-jar $($service.jar)/target/$($service.jar)-0.0.1-SNAPSHOT.jar" -NoNewWindow -RedirectStandardOutput "logs/$($service.name.ToLower()).log" -RedirectStandardError "logs/$($service.name.ToLower())_error.log"
}

# Wait for core services to be up before starting Gateway
foreach ($service in $services) {
    Wait-ForPort -Port $service.port -TimeoutSeconds 120
}

Write-Host "Starting Gateway (Force Port 8090)..." -ForegroundColor Yellow
$gatewayArgs = @("-Dserver.port=8090", "-jar", "finflow-gateway/target/finflow-gateway-0.0.1-SNAPSHOT.jar")
Start-Process "java" -ArgumentList $gatewayArgs -NoNewWindow -RedirectStandardOutput "logs/gateway.log" -RedirectStandardError "logs/gateway_error.log"

if (Wait-ForPort -Port 8090 -TimeoutSeconds 120) {
    Write-Host "All services started successfully." -ForegroundColor Green
} else {
    Write-Host "Gateway failed to start." -ForegroundColor Red
}


