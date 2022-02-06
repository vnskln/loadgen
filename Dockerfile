FROM openjdk:11-jre-slim
ARG JAR_FILE=target/loadgen-*.jar
WORKDIR /usr/local/runme
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UseContainerSupport","-jar","app.jar"]