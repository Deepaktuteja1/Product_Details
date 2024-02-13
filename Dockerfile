FROM openjdk:17-jdk
COPY target/springboot-mysql-docker.jar .
COPY pom.xml /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","springboot-mysql-docker.jar"]

