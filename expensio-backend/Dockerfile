# ====== Stage 1: Build the JAR ======
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY src ./src

# Package the Spring Boot app
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# ====== Stage 2: Run the JAR ======
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
