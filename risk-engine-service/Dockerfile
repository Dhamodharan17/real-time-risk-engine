# Stage 1: Build stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY target/*.jar /app/app.jar

EXPOSE 8080

# Use ENTRYPOINT for the fixed command, allowing JVM_OPTS to be passed at runtime
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar /app/app.jar"]

#mvn clean package
#docker build -t risk-engine-service:v1 .
#docker run -p 8080:8080 risk-engine-service:v1