FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/yongDiary.jar
COPY ${JAR_FILE} yongDiary.jar

ENTRYPOINT [ ""java", "-jar", "/yongDiary.jar" ]