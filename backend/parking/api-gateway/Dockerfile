# parent-build stage
FROM maven:3.8.2-openjdk-17-slim AS parent-build
WORKDIR /workspace
COPY ./backend/parking .
RUN mvn install -DskipTests

# build stage
FROM maven:3.8.2-openjdk-17-slim AS build
WORKDIR /workspace/api-gateway
COPY --from=parent-build /root/.m2 /root/.m2
COPY ./backend/parking/api-gateway .
RUN mvn package -DskipTests

# final stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /workspace/api-gateway/target/*.jar ./app.jar
EXPOSE 8761
CMD ["java", "-jar", "/app/app.jar"]
