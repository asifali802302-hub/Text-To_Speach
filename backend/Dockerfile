FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jdk

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

RUN pip3 install --break-system-packages edge-tts

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY tts.py .

RUN mkdir -p generated-audio

ENV PORT=8080

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]