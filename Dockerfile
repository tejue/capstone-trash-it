FROM --platform=linux/amd64 openjdk:21
LABEL maintainer="teresa"
EXPOSE 8080
ADD backend/target/trash-it.jar trash-it.jar
CMD [ "sh", "-c", "java -jar /trash-it.jar" ]