FROM openjdk:17

WORKDIR /

COPY build/libs/rinhacompilers-0.0.1-SNAPSHOT-all.jar /compiler.jar

ENTRYPOINT ["java", "-jar", "compiler.jar"]