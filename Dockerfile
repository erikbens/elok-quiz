#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/elok-quiz/src
COPY pom.xml /home/elok-quiz
WORKDIR /home/elok-quiz
RUN mvn -Pprod clean package -DskipTests

#
# Package stage
#
FROM openjdk:8-jdk-alpine
COPY --from=build /home/elok-quiz/target/quiz.jar /home/elok-quiz-run/quiz.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/home/elok-quiz-run/quiz.jar"]