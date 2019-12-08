FROM openjdk:11-jre

RUN mkdir /app

WORKDIR /app

COPY ./build/libs/shortit.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/shortit.jar"]