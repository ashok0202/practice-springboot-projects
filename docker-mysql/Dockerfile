FROM openjdk:17-jdk
ADD target/docker-mysql.jar docker-mysql.jar
ENTRYPOINT ["java", "-jar", "/docker-mysql.jar"]