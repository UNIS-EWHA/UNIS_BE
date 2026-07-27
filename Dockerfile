# ---- Build stage ----
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew --no-daemon dependencies

COPY src ./src
RUN ./gradlew --no-daemon bootJar -x test

# ---- Run stage ----
FROM eclipse-temurin:17-jre-alpine AS run
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

# Render는 PORT 환경변수로 리스닝 포트를 주입하며, application-prod.yml의
# server.port=${PORT:8080} 설정이 이를 읽어 바인딩한다.
ENTRYPOINT ["java", "-jar", "app.jar"]
