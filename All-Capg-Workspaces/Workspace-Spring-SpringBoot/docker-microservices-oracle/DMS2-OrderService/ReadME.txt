
// Testing also should pass
>mvn clean package




Skip Tests While Building JAR
// It is working 
mvn clean package -DskipTests


mvn clean package -Dmaven.test.skip=true