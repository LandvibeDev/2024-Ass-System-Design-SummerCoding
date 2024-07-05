# Use the official Gradle image to create a build artifact.
FROM gradle:8.8-jdk17 AS build
WORKDIR /home/app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build -x test

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
COPY --from=build /home/app/build/libs/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
