# --- build stage ---
FROM gradle:9.2.1-jdk17 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./

COPY src ./src

RUN gradle build -x test

# --- runtime stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# копируем собранный jar из стадии сборки
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
