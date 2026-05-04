FROM ubuntu:26.04
LABEL authors="surenk"

#Avoid prompts during installation
ENV DEBIAN_FRONTEND=noninteractive

#Install java and remove all the metadata from apt in the var diretory to keep the image small
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

WORKDIR /app

COPY app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]