


DMS1-CustomerService
DMS2-OrderService

- docker-compose.yml has:

build: ./customer-service
build: ./order-service




docker compose up
docker compose up -d
docker compose down
docker compose ps
docker compose logs
docker compose restar
docker compose up
docker compose up --build




MAIN REASONS THIS ERROR HAPPENS (COMMON IN INDIA)

Docker Desktop not fully logged in
 Corporate / college network blocks HTTPS
 ----------------------------------------

Proxy / firewall issue

Old Docker Desktop version

Antivirus / VPN interference

FIX 2: Restart Docker Desktop (IMPORTANT)

Exit Docker Desktop completely

Wait 30 seconds

Start Docker Desktop again

Wait until status shows Running

Retry: