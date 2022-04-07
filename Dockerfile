FROM openjdk:11
COPY build/libs/demo-application-0.0.1-SNAPSHOT.jar /url-shortner.jar
ENTRYPOINT ["java","-jar","/url-shortner.jar"]
