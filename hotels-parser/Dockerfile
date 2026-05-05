FROM eclipse-temurin:21-jdk as build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true

COPY src src

RUN ./gradlew clean bootJar -x test --no-daemon --build-cache

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
