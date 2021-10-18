FROM openjdk:11
ADD target/web-mvc.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]