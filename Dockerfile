#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY quiz-backend/src /home/elok-quiz/quiz-backend/src
COPY quiz-backend/pom.xml /home/elok-quiz/quiz-backend
COPY pom.xml /home/elok-quiz
#COPY ../pom.xml /home/
WORKDIR /home/elok-quiz
RUN mvn -Pprod clean package -DskipTests

#
# Package stage
#
FROM openjdk:8-jdk-alpine
COPY --from=build /home/elok-quiz/quiz-backend/target/quiz-backend.jar /home/elok-quiz-run/quiz-backend.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/home/elok-quiz-run/quiz-backend.jar"]