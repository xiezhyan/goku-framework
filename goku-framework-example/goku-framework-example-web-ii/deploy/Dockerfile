FROM openjdk:17.0.2
MAINTAINER xiezhyan@126.com

ENV JAVA_OPS=""
WORKDIR /opt

ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} /opt/app.jar

ARG EXPOSE_PORT=8080
EXPOSE ${EXPOSE_PORT}

ENTRYPOINT ["sh","-c","java -Dfile.encoding=utf8 -Dserver.port=${EXPOSE_PORT} $JAVA_OPS -jar /opt/app.jar"]

# docker build -t enjoy-reader-gateway:v0.0.1 .
# docker build -t enjoy-reader-auth:v0.0.1 --build-arg JAR_FILE=target/*.jar --build-arg EXPOSE_PORT=9911 .
# docker run -d --env-file .env --name test goku-example:0.0.3