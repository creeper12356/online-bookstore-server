# FROM maven:3.9.5 AS build
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src ./src
# RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

RUN sed -i 's|http://deb.debian.org/debian|https://mirrors.tuna.tsinghua.edu.cn/debian|g' /etc/apt/sources.list && \
    sed -i 's|http://security.debian.org/debian-security|https://mirrors.tuna.tsinghua.edu.cn/debian-security|g' /etc/apt/sources.list


COPY ./.mvn/wrapper/settings.xml /root/.m2/settings.xml
RUN apt-get update && apt-get upgrade -y && apt-get install git vim -y
EXPOSE 8080
CMD ["tail", "-f", "/dev/null"]