FROM adoptopenjdk/openjdk11:latest

COPY target/drugs-api-*.jar /drugs-api.jar
COPY conf/config.yaml /config.yaml

CMD ["java", "-jar", "/drugs-api.jar", "server", "/config.yaml"]