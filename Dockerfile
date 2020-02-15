FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /target/quiz.jar quiz.jar
ENTRYPOINT ["java","-jar","quiz.jar"]
EXPOSE 8888:8888