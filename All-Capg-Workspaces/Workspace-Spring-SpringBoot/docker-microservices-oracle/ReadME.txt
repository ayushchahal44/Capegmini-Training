Step-1
1. goto  DMS1-CustomerService 
cd DMS1-CustomerService
mvn clean package
  or
mvn clean package -DskipTests

Step-2
2.  goto  DMS1-CustomerService
cd ../DMS2-OrderService
mvn clean package
or 
mvn clean package -DskipTests

Step-3

d:\docker-microservice-oracle>

docker compose down -v
docker compose up --build


http://localhost:7072/orders



