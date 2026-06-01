FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN apk add --no-cache maven && mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
RUN addgroup -S parking && adduser -S parking -G parking
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar
USER parking
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
