FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/com.loyaltypartner.elok.quiz.jar /quiz.jar
ENTRYPOINT ["java","-jar","/quiz.jar"]
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Dspring.profiles.active=docker -Djava.security.egd=file:/dev/./urandom -jar /quiz.war" ]