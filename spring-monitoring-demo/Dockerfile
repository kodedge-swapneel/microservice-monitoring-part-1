FROM openjdk:17-jdk-slim
EXPOSE 8080
ADD build/libs/spring-monitoring-demo.jar spring-monitoring-demo.jar
ENTRYPOINT ["java","-jar","/spring-monitoring-demo.jar"]