FROM openjdk:11.0.10-jre-slim-buster

ENV TZ=Europe/Kiev
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN apt-get update

RUN groupadd bookinggroup
RUN useradd -G bookinggroup hoteluser
USER hoteluser:bookinggroup

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/app.jar"]
