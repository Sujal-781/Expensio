# Use Maven image to build the app
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Package the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
