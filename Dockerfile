FROM gradle:latest AS BUILD

WORKDIR /
COPY . .
RUN ./compile-grammar.sh
RUN gradle shadowJar

FROM openjdk:latest
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=BUILD / .
ENTRYPOINT ["java", "-jar", "/app/build/libs/rinhacompilers-0.0.1-SNAPSHOT-all.jar"]