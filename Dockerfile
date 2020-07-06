FROM openjdk:8-alpine

COPY target/dependency-jars/*.jar  $HOME/service/dependency-jars/
COPY target/service.jar $HOME/service/service.jar
ADD entrypoint.sh $HOME/service/entrypoint.sh
WORKDIR $HOME/service/
CMD ./entrypoint.sh