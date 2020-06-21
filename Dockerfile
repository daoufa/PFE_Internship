FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8086
ADD target/rechercheStagePFE.jar recherchestagepfe.jar
ENTRYPOINT ["java","-jar","/recherchestagepfe.jar"]