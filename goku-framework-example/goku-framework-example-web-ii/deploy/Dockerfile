FROM openjdk:17.0.2
MAINTAINER xiezhyan@126.com

ENV JAVA_OPS=""
ENV JAVA_ARGS=""
WORKDIR /opt

ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

ARG EXPOSE_PORT=8080
EXPOSE ${EXPOSE_PORT}

ENTRYPOINT ["sh","-c","java $JAVA_OPS -Dfile.encoding=utf8 -Djava.security.egd=file:/dev/./urandom -Dserver.port=${EXPOSE_PORT} -jar app.jar $JAVA_ARGS"]

# docker build -t enjoy-reader-gateway:v0.0.1 .
# docker build -t enjoy-reader-auth:v0.0.1 --build-arg JAR_FILE=target/*.jar --build-arg EXPOSE_PORT=9911 .
# docker run -d --env-file .env --name test goku-example:0.0.3