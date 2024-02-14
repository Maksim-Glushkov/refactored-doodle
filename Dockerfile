FROM openjdk:17 AS builder
COPY . /app
COPY src /app/src
WORKDIR /app
RUN ./gradlew build

FROM openjdk:17
COPY --from=builder /app/build/libs/demo-application-0.0.1-SNAPSHOT.jar /url-shortener.jar
ENTRYPOINT ["java","-jar","/url-shortener.jar"]