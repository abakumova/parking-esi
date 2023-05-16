# parent-build stage
FROM maven:3.8.2-openjdk-17-slim AS parent-build
WORKDIR /workspace
COPY ./backend/parking .
RUN mvn install -DskipTests

# build stage
FROM maven:3.8.2-openjdk-17-slim AS build
WORKDIR /workspace/booking
COPY --from=parent-build /root/.m2 /root/.m2
COPY ./backend/parking/booking .
RUN mvn package -DskipTests

# final stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /workspace/booking/target/*.jar ./app.jar
EXPOSE 8761
CMD ["java", "-jar", "/app/app.jar"]
