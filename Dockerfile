FROM openjdk:11-jre-slim
ARG JAR_FILE=target/loadgen-0.0.1.jar
WORKDIR /usr/local/runme
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions"."-XX:+UseCGroupMemoryLimitForHeap","-jar","app.jar"]