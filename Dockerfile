FROM gradle:7.3.3-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test --no-daemon --no-watch-fs

FROM openjdk:17
LABEL maintainer="rheafernandes.dev@gmail.com"
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]
